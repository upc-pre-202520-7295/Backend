package com.betalyze.predictionandanalytics.infrastructure.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.MatchRepository;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.TeamRepository;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningMatchPredictionDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningMatchTrainDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningPrection;
import com.betalyze.predictionandanalytics.infrastructure.ml.MachineLearningModelManager;
import com.betalyze.shared.application.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/predictions")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Predictions", description = "Match prediction and ML model endpoints")
public class PredictionController {
  private final MatchRepository matchRepository;
  private final TeamRepository teamRepository;
  private final MachineLearningModelManager manager;

  @PostMapping("/train")
  @Operation(summary = "Manually trigger model training", description = "Admin only - Retrains the ML model with latest data")
  public ResponseEntity<Response<Void>> trainModel() {
    List<MachineLearningMatchTrainDto> matches = matchRepository.findAll().stream().map(match -> {
      return new MachineLearningMatchTrainDto(match.getHomeTeam().getCode(),
          match.getDetails().getHomeTeamScore(),
          match.getAwayTeam().getCode(), match.getDetails().getAwayTeamScore(), 350L);
    }).collect(Collectors.toList());

    log.info("Starting model training");

    manager.loadModel();
    log.info("Model loaded successfully");

    manager.train(matches);
    log.info("Model training completed successfully");

    manager.saveModel();
    log.info("Model saved successfully");

    return ResponseEntity.ok(null);
  }

  @PostMapping("/predict/{homeTeamId}/{awayTeamId}")
  @Operation(summary = "Manually trigger prediction generation", description = "Admin only - Generates predictions for today's matches")
  public ResponseEntity<Response<MachineLearningPrection>> generatePredictions(@PathVariable UUID homeTeamId,
      @PathVariable UUID awayTeamId) {
    log.info("Generating predictions for {} vs {}", homeTeamId, awayTeamId);

    Team homeTeam = teamRepository.findById(homeTeamId).orElse(null);
    Team awayTeam = teamRepository.findById(awayTeamId).orElse(null);

    if (homeTeam == null) {
      return ResponseEntity.ok(Response.error("Home team not found"));
    }

    if (awayTeam == null) {
      return ResponseEntity.ok(Response.error("Away team not found"));
    }

    log.info("Starting prediction generation");

    manager.loadModel();
    log.info("Model loaded successfully");

    MachineLearningPrection prediction = manager
        .predict(new MachineLearningMatchPredictionDto(homeTeam.getCode(),
            awayTeam.getCode(), 350L));
    log.info("Prediction generated successfully");

    return ResponseEntity.ok(Response.success("Predictions generated successfully", prediction));

  }

  // @GetMapping("/today")
  // @Operation(summary = "Get today's predictions", description = "Returns all
  // predictions for today's matches ordered by confidence")
  // public ResponseEntity<Response<List<PredictionDto>>> getTodayPredictions() {
  // List<PredictionDto> predictions = predictionService.getTodayPredictions();
  // return ResponseEntity.ok(Response.success(predictions));
  // }
  //
  // @GetMapping("/match/{matchId}")
  // @Operation(summary = "Get prediction for specific match")
  // public ResponseEntity<Response<PredictionDto>> getPredictionForMatch(
  // @Parameter(description = "External match ID") @PathVariable String matchId) {
  // PredictionDto prediction = predictionService.getPredictionForMatch(matchId);
  // if (prediction == null) {
  // return ResponseEntity.ok(Response.error("No prediction found for this
  // match"));
  // }
  // return ResponseEntity.ok(Response.success(prediction));
  // }
  //
  // @GetMapping("/team/{teamId}")
  // @Operation(summary = "Get predictions for specific team today")
  // public ResponseEntity<Response<List<PredictionDto>>> getPredictionsByTeam(
  // @Parameter(description = "Team ID") @PathVariable String teamId) {
  // List<PredictionDto> predictions =
  // predictionService.getPredictionsByTeam(teamId);
  // return ResponseEntity.ok(Response.success(predictions));
  // }
  //
  // @GetMapping("/model/metrics")
  // @Operation(summary = "Get current ML model metrics", description = "Returns
  // performance metrics of the latest trained model")
  // public ResponseEntity<Response<ModelMetricsDto>> getModelMetrics() {
  // ModelMetricsDto metrics = predictionService.getCurrentModelMetrics();
  // if (metrics == null) {
  // return ResponseEntity.ok(Response.error("No model metrics available yet"));
  // }
  // return ResponseEntity.ok(Response.success(metrics));
  // }
  //
  // @PostMapping("/generate")
  // @PreAuthorize("hasRole('ADMIN')")
  // @Operation(summary = "Manually trigger prediction generation", description =
  // "Admin only - Generates predictions for today's matches")
  // public ResponseEntity<Response<List<PredictionDto>>> generatePredictions() {
  // List<PredictionDto> predictions = predictionService.generatePredictions();
  // return ResponseEntity.ok(Response.success("Predictions generated
  // successfully", predictions));
  // }
  //
  // @PostMapping("/train")
  // @PreAuthorize("hasRole('ADMIN')")
  // @Operation(summary = "Manually trigger model training", description = "Admin
  // only - Retrains the ML model with latest data")
  // public ResponseEntity<Response<Void>> trainModel() {
  // predictionService.trainModel();
  // return ResponseEntity.ok(Response.success("Model training completed
  // successfully", null));
  // }
}
