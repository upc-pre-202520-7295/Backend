package com.betalyze.predictionandanalytics.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineLearningPrection {
  private String winner;
  private double winnerPrecision;
  private double homeTeamGoals;
  private double awayTeamGoals;
}
