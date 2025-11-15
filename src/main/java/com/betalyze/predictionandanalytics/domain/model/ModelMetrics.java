package com.betalyze.predictionandanalytics.domain.model;

import com.betalyze.shared.domain.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name = "model_metrics")
@Getter
@Setter
@NoArgsConstructor
public class ModelMetrics extends Entity {
  @Column(name = "model_version", nullable = false, unique = true)
  private String modelVersion;

  @Column(name = "training_date", nullable = false)
  private LocalDateTime trainingDate;

  @Column(name = "total_predictions", nullable = false)
  private Integer totalPredictions = 0;

  @Column(name = "correct_predictions", nullable = false)
  private Integer correctPredictions = 0;

  @Column(name = "accuracy", nullable = false)
  private Double accuracy = 0.0;

  @Column(name = "precision_score", nullable = false)
  private Double precision = 0.0;

  @Column(name = "recall_score", nullable = false)
  private Double recall = 0.0;

  @Column(name = "f1_score", nullable = false)
  private Double f1Score = 0.0;

  @Column(name = "training_data_size", nullable = false)
  private Integer trainingDataSize;

  @Column(name = "epochs", nullable = false)
  private Integer epochs;

  @Column(name = "learning_rate", nullable = false)
  private Double learningRate;

  public ModelMetrics(String modelVersion, LocalDateTime trainingDate,
      Integer trainingDataSize, Integer epochs, Double learningRate) {
    this.modelVersion = modelVersion;
    this.trainingDate = trainingDate;
    this.trainingDataSize = trainingDataSize;
    this.epochs = epochs;
    this.learningRate = learningRate;
  }

  public void updateAccuracy() {
    if (totalPredictions > 0) {
      this.accuracy = (double) correctPredictions / totalPredictions * 100;
    }
  }

  public void incrementPredictions(boolean wasCorrect) {
    this.totalPredictions++;
    if (wasCorrect) {
      this.correctPredictions++;
    }
    updateAccuracy();
  }
}
