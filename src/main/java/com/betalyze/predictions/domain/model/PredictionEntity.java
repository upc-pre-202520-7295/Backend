package com.betalyze.predictions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "predictions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "match_id", nullable = false)
    private UUID matchId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Campos principales de predicción
    @Column(name = "home_win_prob")
    private Double homeWinProb;

    @Column(name = "draw_prob")
    private Double drawProb;

    @Column(name = "away_win_prob")
    private Double awayWinProb;

    @Column(name = "predicted_outcome")
    private String predictedOutcome;

    @Column(name = "result_confidence")
    private String resultConfidence;

    @Column(name = "result_confidence_score")
    private Double resultConfidenceScore;

    // Goles esperados
    @Column(name = "home_expected_goals")
    private Double homeExpectedGoals;

    @Column(name = "away_expected_goals")
    private Double awayExpectedGoals;

    @Column(name = "total_expected_goals")
    private Double totalExpectedGoals;

    // Over/Under
    @Column(name = "over_2_5_prob")
    private Double over25Prob;

    @Column(name = "under_2_5_prob")
    private Double under25Prob;

    @Column(name = "over_under_recommendation")
    private String overUnderRecommendation;

    @Column(name = "over_under_confidence")
    private String overUnderConfidence;

    @Column(name = "over_under_confidence_score")
    private Double overUnderConfidenceScore;

    // Both Teams To Score
    @Column(name = "btts_probability")
    private Double bttsProbability;

    @Column(name = "btts_recommendation")
    private String bttsRecommendation;

    // Raw payload
    @Column(name = "raw_payload", columnDefinition = "jsonb")
    private String rawPayload;

    // Resultado real
    @Column(name = "actual_outcome")
    private String actualOutcome;

    @Column(name = "correct")
    private Boolean correct;

    // Campos legacy (para compatibilidad)
    @Column(name = "predicted_away_score")
    private Double predictedAwayScore;

    @Column(name = "predicted_home_score")
    private Double predictedHomeScore;

    @Column(name = "away_team_name")
    private String awayTeamName;

    @Column(name = "away_win_probability")
    private Double awayWinProbability;

    @Column(name = "confidence")
    private Double confidence;

    @Column(name = "draw_probability")
    private Double drawProbability;

    @Column(name = "home_team_name")
    private String homeTeamName;

    @Column(name = "home_win_probability")
    private Double homeWinProbability;

    @Column(name = "match_date")
    private LocalDateTime matchDate;

    @Column(name = "match_external_id")
    private String matchExternalId;

    @Column(name = "model_version")
    private String modelVersion;

    // Imágenes (vienen del JOIN)
    @Transient
    private String homeTeamImage;

    @Transient
    private String awayTeamImage;
}
