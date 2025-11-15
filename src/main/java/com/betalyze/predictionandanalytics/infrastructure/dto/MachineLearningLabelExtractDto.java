package com.betalyze.predictionandanalytics.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineLearningLabelExtractDto {
  private Long homeTeamId;
  private Integer homeTeamGoals;

  private Long awayTeamId;
  private Integer awayTeamGoals;

  private Long totalTeams;
}
