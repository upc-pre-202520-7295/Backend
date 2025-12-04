package com.betalyze.predictions.infrastructure.persistence.jpa;

import java.util.UUID;

import com.betalyze.predictions.domain.model.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepo extends JpaRepository<MatchEntity, UUID> {
}
