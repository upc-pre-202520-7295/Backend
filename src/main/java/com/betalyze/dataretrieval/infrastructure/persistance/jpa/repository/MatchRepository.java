package com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {
  Optional<Match> findByExternalId(String externalId);

  List<Match> findByMatchDateBetweenOrderByMatchDate(LocalDateTime start, LocalDateTime end);

  List<Match> findAll();
}
