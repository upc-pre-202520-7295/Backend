package com.betalyze.predictionandanalytics.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.MatchRepository;
import com.betalyze.predictionandanalytics.application.dto.ModelMetricsDto;
import com.betalyze.predictionandanalytics.application.dto.PredictionDto;
import com.betalyze.predictionandanalytics.domain.model.ModelMetrics;
import com.betalyze.predictionandanalytics.domain.model.Prediction;
import com.betalyze.predictionandanalytics.infrastructure.ml.MachineLearningModelManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PredictionService {
  private final MachineLearningModelManager mlManager;
  private final MatchRepository matchRepository;
  //
  // @Transactional
  // public void trainModel() {
  //   log.info("Starting model training");
  //   try {
  //     String modelVersion = "v_" + LocalDateTime.now()
  //         .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
  //
  //     List<Match> historicalMatches = matchRepository.findAll();
  //     log.info("Training with {} historical matches", historicalMatches.size());
  //
  //     mlModel.train(historicalMatches, teamStatisticsRepository);
  //
  //     ModelMetrics metrics = new ModelMetrics(
  //         modelVersion,
  //         LocalDateTime.now(),
  //         historicalMatches.size(),
  //         100,
  //         0.001);
  //     modelMetricsRepository.save(metrics);
  //     log.info("Model training completed. Version: {}", modelVersion);
  //   } catch (Exception e) {
  //     log.error("Error training model", e);
  //     throw new RuntimeException("Model training failed", e);
  //   }
  // }
  //
  // @Transactional
  // public List<PredictionDto> generatePredictions() {
  //   log.info("Starting prediction generation");
  //
  //   LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
  //   LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
  //
  //   List<Match> todayMatches = matchRepository.findTodayMatches(startOfDay,
  //       endOfDay);
  //   log.info("Generating predictions for {} matches", todayMatches.size());
  //
  //   List<Prediction> predictions = todayMatches.stream()
  //       .map(match -> {
  //         try {
  //           Prediction existing = predictionRepository
  //               .findByMatchExternalId(match.getExternalId())
  //               .orElse(null);
  //
  //           if (existing != null) {
  //             log.debug("Prediction already exists for match {}", match.getExternalId());
  //             return existing;
  //           }
  //
  //           return mlModel.predict(match, teamStatisticsRepository);
  //         } catch (Exception e) {
  //           log.error("Error predicting match {}", match.getExternalId(), e);
  //           return null;
  //         }
  //       })
  //       .filter(prediction -> prediction != null)
  //       .collect(Collectors.toList());
  //
  //   List<Prediction> savedPredictions = predictionRepository.saveAll(predictions);
  //   log.info("Generated and saved {} predictions", savedPredictions.size());
  //
  //   return savedPredictions.stream()
  //       .map(this::mapToDto)
  //       .collect(Collectors.toList());
  // }
  //
  // @Transactional(readOnly = true)
  // public List<PredictionDto> getTodayPredictions() {
  //   LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
  //   LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
  //
  //   predictionRepository.findTodayPredictionsOrderedByConfidence(startOfDay,
  //       endOfDay)
  //       .stream()
  //       .map(this::mapToDto)
  //       .collect(Collectors.toList());
  // }
  //
  // @Transactional(readOnly = true)
  // public PredictionDto getPredictionForMatch(String matchExternalId) {
  //   return predictionRepository.findByMatchExternalId(matchExternalId)
  //       .map(this::mapToDto)
  //       .orElse(null);
  // }
  //
  // @Transactional(readOnly = true)
  // public List<PredictionDto> getPredictionsByTeam(String teamId) {
  //   LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
  //   LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
  //
  //   return predictionRepository.findPredictionsByTeamAndDateRange(teamId,
  //       startOfDay, endOfDay)
  //       .stream()
  //       .map(this::mapToDto)
  //       .collect(Collectors.toList());
  // }
  //
  // @Transactional(readOnly = true)
  // public ModelMetricsDto getCurrentModelMetrics() {
  //   return null;
  //   return modelMetricsRepository.findFirstByOrderByTrainingDateDesc()
  //       .map(this::mapToDto)
  //       .orElse(null);
  // }
  //
  // private PredictionDto mapToDto(Prediction prediction) {
  //   return new PredictionDto(
  //       prediction.getId(),
  //       prediction.getMatchExternalId(),
  //       prediction.getHomeTeamId(),
  //       prediction.getHomeTeamName(),
  //       prediction.getAwayTeamId(),
  //       prediction.getAwayTeamName(),
  //       prediction.getMatchDate(),
  //       prediction.getPredictedOutcome().name(),
  //       prediction.getHomeWinProbability(),
  //       prediction.getDrawProbability(),
  //       prediction.getAwayWinProbability(),
  //       prediction.getConfidence(),
  //       prediction.getPredictedHomeScore(),
  //       prediction.getPredictedAwayScore(),
  //       prediction.getModelVersion(),
  //       prediction.getActualOutcome() != null ? prediction.getActualOutcome().name() : null,
  //       prediction.getCorrect());
  // }
  //
  // private ModelMetricsDto mapToDto(ModelMetrics metrics) {
  //   return new ModelMetricsDto(
  //       metrics.getId(),
  //       metrics.getModelVersion(),
  //       metrics.getTrainingDate(),
  //       metrics.getTotalPredictions(),
  //       metrics.getCorrectPredictions(),
  //       metrics.getAccuracy(),
  //       metrics.getPrecision(),
  //       metrics.getRecall(),
  //       metrics.getF1Score(),
  //       metrics.getTrainingDataSize(),
  //       metrics.getEpochs(),
  //       metrics.getLearningRate());
  // }
}
