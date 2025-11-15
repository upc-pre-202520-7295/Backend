package com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betalyze.dataretrieval.domain.model.model.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
  public Optional<Team> findById(UUID id);

  public Optional<Team> findByTeamExternalId(String teamExternalId);
}
