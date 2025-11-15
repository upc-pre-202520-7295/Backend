package com.betalyze.predictionandanalytics.domain.repository;

import com.betalyze.predictionandanalytics.domain.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, UUID> {
  Optional<Prediction> findByMatchExternalId(String matchExternalId);

  List<Prediction> findByMatchDateBetween(LocalDateTime start, LocalDateTime end);

  @Query("SELECT p FROM Prediction p WHERE p.matchDate >= :start AND p.matchDate < :end ORDER BY p.confidence DESC")
  List<Prediction> findTodayPredictionsOrderedByConfidence(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query("SELECT p FROM Prediction p WHERE (p.homeTeamId = :teamId OR p.awayTeamId = :teamId) " +
      "AND p.matchDate >= :start AND p.matchDate < :end ORDER BY p.matchDate")
  List<Prediction> findPredictionsByTeamAndDateRange(
      @Param("teamId") String teamId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  List<Prediction> findByModelVersion(String modelVersion);

  @Query("SELECT COUNT(p) FROM Prediction p WHERE p.modelVersion = :version AND p.correct = true")
  Long countCorrectPredictionsByModelVersion(@Param("version") String modelVersion);

  @Query("SELECT COUNT(p) FROM Prediction p WHERE p.modelVersion = :version")
  Long countTotalPredictionsByModelVersion(@Param("version") String modelVersion);
}
