package com.betalyze.predictions.interfaces.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.betalyze.predictions.infrastructure.persistence.jpa.PredictionRepo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/predictions")
@RequiredArgsConstructor
@Tag(name = "Predictions", description = "Prediction endpoints")
public class PredictionController {

    private final PredictionRepo predictionRepo;

    @GetMapping
    @Operation(summary = "Get predictions with optional filters")
    public ResponseEntity<PredictionListResponse> getPredictions(
            @Parameter(description = "Season year (e.g., 2023, 2024)")
            @RequestParam(required = false) String season,
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59) : null;

        List<Object[]> raw;

        if (season != null && start != null && end != null) {
            raw = predictionRepo.findBySeasonAndDateRange(season, start, end);
        } else if (season != null) {
            raw = predictionRepo.findBySeason(season);
        } else if (start != null && end != null) {
            raw = predictionRepo.findByDateRange(start, end);
        } else {
            raw = predictionRepo.findAllWithImages();
        }

        List<PredictionResponse> responses = raw.stream()
                .map(this::mapRowToResponse)
                .toList();

        return ResponseEntity.ok(new PredictionListResponse(true, responses));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get prediction by ID")
    public ResponseEntity<PredictionResponse> getPredictionById(@PathVariable UUID id) {
        return predictionRepo.findByIdWithImages(id)
                .map(this::mapRowToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ======================================================
    // MAPEADOR: Object[] -> PredictionResponse
    // ======================================================
    private PredictionResponse mapRowToResponse(Object[] row) {
        // Indices
        UUID predictionId           = (UUID) row[0];
        UUID matchId                = (UUID) row[1];

        LocalDateTime createdAt     = convertToLocalDateTime(row[2]);
        LocalDateTime updatedAt     = convertToLocalDateTime(row[3]);

        Double homeWinProb          = (Double) row[4];
        Double drawProb             = (Double) row[5];
        Double awayWinProb          = (Double) row[6];
        String predictedOutcome     = (String) row[7];
        String resultConfidence     = (String) row[8];
        Double resultConfScore      = (Double) row[9];
        Double homeExpGoals         = (Double) row[10];
        Double awayExpGoals         = (Double) row[11];
        Double totalExpGoals        = (Double) row[12];
        Double over25Prob           = (Double) row[13];
        Double under25Prob          = (Double) row[14];
        String ouRecommendation     = (String) row[15];
        String ouConfidence         = (String) row[16];
        Double ouConfScore          = (Double) row[17];
        Double bttsProb             = (Double) row[18];
        String bttsRecommendation   = (String) row[19];
        String actualOutcome        = (String) row[20];
        Boolean correct             = (Boolean) row[21];
        String homeImg              = (String) row[22];
        String awayImg              = (String) row[23];
        String leagueName           = (String) row[24];
        String season               = (String) row[25];
        String homeTeamName         = (String) row[26];
        String awayTeamName         = (String) row[27];

        LocalDateTime matchDate     = convertToLocalDateTime(row[28]);

        // Result
        ResultDTO result = new ResultDTO(
                orZero(homeWinProb),
                orZero(drawProb),
                orZero(awayWinProb),
                predictedOutcome != null ? predictedOutcome : "UNKNOWN",
                resultConfidence != null ? resultConfidence : "UNKNOWN",
                orZero(resultConfScore)
        );

        GoalsDTO goals = new GoalsDTO(
                orZero(homeExpGoals),
                orZero(awayExpGoals),
                orZero(totalExpGoals)
        );

        OverUnderDTO overUnder = new OverUnderDTO(
                orZero(over25Prob),
                orZero(under25Prob),
                ouRecommendation != null ? ouRecommendation : "UNKNOWN",
                ouConfidence != null ? ouConfidence : "UNKNOWN",
                orZero(ouConfScore)
        );

        BothTeamsScoreDTO btts = new BothTeamsScoreDTO(
                orZero(bttsProb),
                bttsRecommendation != null ? bttsRecommendation : "UNKNOWN"
        );

        PredictionsDTO predictions = new PredictionsDTO(result, goals, overUnder, btts);

        String date = matchDate != null ? matchDate.toLocalDate().toString() : "Unknown";
        String time = matchDate != null ? matchDate.toLocalTime().toString() : "Unknown";

        MatchInfoDTO matchInfo = new MatchInfoDTO(
                date,
                time,
                leagueName != null ? leagueName : "Unknown League",
                season != null ? season : "Unknown Season"
        );

        return new PredictionResponse(
                true,
                predictions,
                homeTeamName != null ? homeTeamName : "Unknown",
                awayTeamName != null ? awayTeamName : "Unknown",
                homeImg,
                awayImg,
                matchInfo
        );
    }


    // ======================================================
    // UTILITY METHODS
    // ======================================================

    /**
     * Convierte un Timestamp SQL a LocalDateTime
     */
    private LocalDateTime convertToLocalDateTime(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toLocalDateTime();
        }
        if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        }
        return null;
    }

    private Double orZero(Double v) {
        return v != null ? v : 0.0;
    }

    // ======================================================
    // DTOs
    // ======================================================

    public record PredictionListResponse(
            boolean success,
            List<PredictionResponse> data
    ) {}

    public record PredictionResponse(
            boolean success,
            PredictionsDTO predictions,
            String home_team_name,
            String away_team_name,
            String home_team_image,
            String away_team_image,
            MatchInfoDTO match_info
    ) {}

    public record PredictionsDTO(
            ResultDTO result,
            GoalsDTO goals,
            OverUnderDTO over_under,
            BothTeamsScoreDTO both_teams_score
    ) {}

    public record ResultDTO(
            Double home_win_prob,
            Double draw_prob,
            Double away_win_prob,
            String predicted_outcome,
            String confidence,
            Double confidence_score
    ) {}

    public record GoalsDTO(
            Double home_expected,
            Double away_expected,
            Double total_expected
    ) {}

    public record OverUnderDTO(
            Double over_2_5_probability,
            Double under_2_5_probability,
            String recommendation,
            String confidence,
            Double confidence_score
    ) {}

    public record BothTeamsScoreDTO(
            Double probability,
            String recommendation
    ) {}

    public record MatchInfoDTO(
            String date,
            String time,
            String league,
            String season
    ) {}
}