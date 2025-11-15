package com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betalyze.dataretrieval.domain.model.model.entity.MatchDetails;
import com.betalyze.dataretrieval.domain.model.model.entity.MatchStatus;

@Repository
public interface MatchDetailsRepository extends JpaRepository<MatchDetails, UUID> {
  Optional<MatchDetails> findByMatchIdAndMatchStatus(UUID matchId, MatchStatus matchStatus);
}
