package com.betalyze.predictionandanalytics.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelMetricsDto {
  private UUID id;
  private String modelVersion;
  private LocalDateTime trainingDate;
  private Integer totalPredictions;
  private Integer correctPredictions;
  private Double accuracy;
  private Double precision;
  private Double recall;
  private Double f1Score;
  private Integer trainingDataSize;
  private Integer epochs;
  private Double learningRate;
}
