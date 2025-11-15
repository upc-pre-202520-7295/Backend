package com.betalyze.predictionandanalytics.domain.repository;

import com.betalyze.predictionandanalytics.domain.model.ModelMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelMetricsRepository extends JpaRepository<ModelMetrics, UUID> {
  Optional<ModelMetrics> findByModelVersion(String modelVersion);

  Optional<ModelMetrics> findFirstByOrderByTrainingDateDesc();
}
