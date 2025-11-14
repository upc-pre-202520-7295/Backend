package com.betalyze.favorites.infrastructure.persistance.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betalyze.favorites.domain.model.aggregate.FavoriteTeam;

@Repository
public interface FavoriteTeamRepository extends JpaRepository<FavoriteTeam, UUID> {
  List<FavoriteTeam> findByUserId(UUID userId);

  Optional<FavoriteTeam> findByUserIdAndTeamId(UUID userId, UUID teamId);
}
