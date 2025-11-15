package com.betalyze.predictionandanalytics.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineLearningFeatureExtractDto {
  private Long homeTeamId;
  private Long awayTeamId;
  private Long totalTeams;
}
