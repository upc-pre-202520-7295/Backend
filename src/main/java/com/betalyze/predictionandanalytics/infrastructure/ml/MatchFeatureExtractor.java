package com.betalyze.predictionandanalytics.infrastructure.ml;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningFeatureExtractDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningLabelExtractDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MatchFeatureExtractor {

  // Extrae features dinámicamente
  public INDArray extractFeatures(MachineLearningFeatureExtractDto dto) {
    // Ejemplo simple: normalizamos los IDs de los equipos
    double[] features = {
        dto.getHomeTeamId() / (double) dto.getTotalTeams(),
        dto.getAwayTeamId() / (double) dto.getTotalTeams()
    };
    return Nd4j.create(features).reshape(1, features.length);
  }

  // Extrae labels dinámicamente
  public INDArray extractLabels(MachineLearningLabelExtractDto dto) {
    // Winner code: home=0, draw=0.5, away=1
    double winnerCode = 0;
    if (dto.getHomeTeamGoals() > dto.getAwayTeamGoals())
      winnerCode = 0;
    else if (dto.getHomeTeamGoals() < dto.getAwayTeamGoals())
      winnerCode = 1;
    else
      winnerCode = 0.5;

    Double homeGoals = dto.getHomeTeamGoals() / (double) getMaxGoals();
    Double awayGoals = dto.getAwayTeamGoals() / (double) getMaxGoals();

    double[] labels = { winnerCode, homeGoals, awayGoals };
    return Nd4j.create(labels).reshape(1, labels.length);
  }

  public int getFeatureSize() {
    return 2;
  }

  public int getLabelSize() {
    return 3;
  }

  public double getMaxGoals() {
    return 12.0;
  }
}
