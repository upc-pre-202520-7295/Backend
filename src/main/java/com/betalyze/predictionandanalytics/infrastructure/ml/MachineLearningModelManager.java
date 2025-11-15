package com.betalyze.predictionandanalytics.infrastructure.ml;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Component;

import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningFeatureExtractDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningLabelExtractDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningMatchPredictionDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningMatchTrainDto;
import com.betalyze.predictionandanalytics.infrastructure.dto.MachineLearningPrection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MachineLearningModelManager {

  private static final String MODEL_PATH = "models/latest_model.zip";

  private MultiLayerNetwork model;
  private String currentVersion;

  public void train(List<MachineLearningMatchTrainDto> matches) {
    try {
      MatchFeatureExtractor featureExtractor = new MatchFeatureExtractor();

      int inputSize = featureExtractor.getFeatureSize();
      int outputSize = featureExtractor.getLabelSize();

      if (model == null) {
        log.info("Building new model");
        buildModel(inputSize, outputSize);
      }

      int epochs = 50;
      for (int epoch = 0; epoch < epochs; epoch++) {
        for (MachineLearningMatchTrainDto match : matches) {
          INDArray features = featureExtractor.extractFeatures(
              new MachineLearningFeatureExtractDto(match.getHomeTeamId(),
                  match.getAwayTeamId(), match.getTotalTeams()));

          INDArray labels = featureExtractor.extractLabels(
              new MachineLearningLabelExtractDto(match.getHomeTeamId(),
                  match.getHomeTeamGoals(), match.getAwayTeamId(),
                  match.getAwayTeamGoals(), match.getTotalTeams()));

          model.fit(features, labels);
        }
        if (epoch % 10 == 0) {
          log.info("Epoch {}/{}", epoch, epochs);
        }
      }

      currentVersion = "v_" + LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      log.info("Model trained successfully, version {}", currentVersion);

    } catch (Exception e) {
      log.error("Error training model", e);
    }
  }

  public MachineLearningPrection predict(MachineLearningMatchPredictionDto match) {
    MatchFeatureExtractor featureExtractor = new MatchFeatureExtractor();

    INDArray features = featureExtractor.extractFeatures(
        new MachineLearningFeatureExtractDto(match.getHomeTeamId(),
            match.getAwayTeamId(), match.getTotalTeams()));

    INDArray output = model.output(features);

    double winnerCode = output.getDouble(0);
    double homeGoals = output.getDouble(1) * featureExtractor.getMaxGoals();
    double awayGoals = output.getDouble(2) * featureExtractor.getMaxGoals();

    log.info("Winner Code: {}", output.getDouble(0));
    log.info("Home Goals: {}", output.getDouble(1));
    log.info("Away Goals: {}", output.getDouble(2));

    String winner;
    if (winnerCode > 0.5)
      winner = "away";
    else if (winnerCode < 0.5)
      winner = "home";
    else
      winner = "draw";

    return new MachineLearningPrection(winner, output.getDouble(0), Math.round(homeGoals), Math.round(awayGoals));
  }

  public void saveModel() {
    try {
      File modelFile = new File(MODEL_PATH);
      modelFile.getParentFile().mkdirs();
      ModelSerializer.writeModel(model, modelFile, true);
      log.info("Model saved to {}", modelFile.getAbsolutePath());
    } catch (IOException e) {
      log.error("Failed to save model", e);
    }
  }

  public void loadModel() {
    File modelFile = new File(MODEL_PATH);
    if (modelFile.exists()) {
      try {
        model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
        currentVersion = modelFile.getName();
        log.info("Model loaded from {}", modelFile.getAbsolutePath());
      } catch (IOException e) {
        log.warn("Failed to load model, building new one", e);
      }
    }
  }

  private void buildModel(int inputSize, int outputSize) {
    MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
        .seed(123)
        .weightInit(WeightInit.XAVIER)
        .updater(new Adam(0.001))
        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
        .list()
        .layer(new DenseLayer.Builder().nIn(inputSize).nOut(64)
            .activation(Activation.RELU).build())
        .layer(new DenseLayer.Builder().nIn(64).nOut(32)
            .activation(Activation.RELU).build())
        .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
            .nIn(32).nOut(outputSize)
            .activation(Activation.IDENTITY).build())
        .build();

    model = new MultiLayerNetwork(conf);
    model.init();
    currentVersion = "v_" + LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    log.info("New model built successfully, version {}", currentVersion);
  }
}
