package com.betalyze.predictionandanalytics.domain.model;

import com.betalyze.shared.domain.AggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
@Getter
@Setter
@NoArgsConstructor
public class Prediction extends AggregateRoot {
  @Column(name = "match_external_id", nullable = false)
  private String matchExternalId;

  @Column(name = "home_team_id", nullable = false)
  private String homeTeamId;

  @Column(name = "home_team_name", nullable = false)
  private String homeTeamName;

  @Column(name = "away_team_id", nullable = false)
  private String awayTeamId;

  @Column(name = "away_team_name", nullable = false)
  private String awayTeamName;

  @Column(name = "match_date", nullable = false)
  private LocalDateTime matchDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "predicted_outcome", nullable = false)
  private PredictionOutcome predictedOutcome;

  @Column(name = "home_win_probability", nullable = false)
  private Double homeWinProbability;

  @Column(name = "draw_probability", nullable = false)
  private Double drawProbability;

  @Column(name = "away_win_probability", nullable = false)
  private Double awayWinProbability;

  @Column(name = "confidence", nullable = false)
  private Double confidence;

  @Column(name = "predicted_home_score")
  private Double predictedHomeScore;

  @Column(name = "predicted_away_score")
  private Double predictedAwayScore;

  @Column(name = "model_version", nullable = false)
  private String modelVersion;

  @Enumerated(EnumType.STRING)
  @Column(name = "actual_outcome")
  private PredictionOutcome actualOutcome;

  @Column(name = "correct")
  private Boolean correct;

  public Prediction(String matchExternalId, String homeTeamId, String homeTeamName,
      String awayTeamId, String awayTeamName, LocalDateTime matchDate,
      PredictionOutcome predictedOutcome, Double homeWinProbability,
      Double drawProbability, Double awayWinProbability,
      Double confidence, String modelVersion) {
    this.matchExternalId = matchExternalId;
    this.homeTeamId = homeTeamId;
    this.homeTeamName = homeTeamName;
    this.awayTeamId = awayTeamId;
    this.awayTeamName = awayTeamName;
    this.matchDate = matchDate;
    this.predictedOutcome = predictedOutcome;
    this.homeWinProbability = homeWinProbability;
    this.drawProbability = drawProbability;
    this.awayWinProbability = awayWinProbability;
    this.confidence = confidence;
    this.modelVersion = modelVersion;
  }

  public void setActualResult(PredictionOutcome actualOutcome) {
    this.actualOutcome = actualOutcome;
    this.correct = this.predictedOutcome.equals(actualOutcome);
  }

  public void setPredictedScores(Double homeScore, Double awayScore) {
    this.predictedHomeScore = homeScore;
    this.predictedAwayScore = awayScore;
  }
}
