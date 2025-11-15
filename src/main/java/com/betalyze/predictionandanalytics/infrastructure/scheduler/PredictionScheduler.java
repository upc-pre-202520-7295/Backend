package com.betalyze.predictionandanalytics.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.betalyze.predictionandanalytics.application.service.PredictionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PredictionScheduler {
  // private final PredictionService predictionService;
  //
  // @Scheduled(cron = "0 30 6 * * *") // Every day at 6:30 AM
  // public void trainAndPredict() {
  //   log.info("=== Starting daily model training and prediction generation ===");
  //
  //   try {
  //     log.info("Step 1: Retraining ML model...");
  //     predictionService.trainModel();
  //     log.info("✓ Model training completed");
  //
  //     log.info("Step 2: Generating predictions for today's matches...");
  //     predictionService.generatePredictions();
  //     log.info("✓ Predictions generated successfully");
  //
  //     log.info("=== Daily prediction process completed successfully ===");
  //
  //   } catch (Exception e) {
  //     log.error("!!! Error in daily prediction process !!!", e);
  //   }
  // }
  //
  // @Scheduled(cron = "0 0 * * * *") // Every hour
  // public void refreshPredictions() {
  //   log.debug("Refreshing predictions for newly added matches");
  //   try {
  //     predictionService.generatePredictions();
  //   } catch (Exception e) {
  //     log.error("Error refreshing predictions", e);
  //   }
  // }
}
