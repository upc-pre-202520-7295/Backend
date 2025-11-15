package com.betalyze.predictionandanalytics.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionDto {
  private UUID id;
  private String matchExternalId;
  private String homeTeamId;
  private String homeTeamName;
  private String awayTeamId;
  private String awayTeamName;
  private LocalDateTime matchDate;
  private String predictedOutcome;
  private Double homeWinProbability;
  private Double drawProbability;
  private Double awayWinProbability;
  private Double confidence;
  private Double predictedHomeScore;
  private Double predictedAwayScore;
  private String modelVersion;
  private String actualOutcome;
  private Boolean correct;
}
