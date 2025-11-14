package com.betalyze.favorites.application.internal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.favorites.application.outboundservices.ExternalUserManegementService;
import com.betalyze.favorites.domain.model.aggregate.FavoriteTeam;
import com.betalyze.favorites.domain.model.command.AddFavoriteTeamCommand;
import com.betalyze.favorites.domain.model.command.RemoveFavoriteTeamCommand;
import com.betalyze.favorites.domain.service.TeamFavoriteCommandService;
import com.betalyze.favorites.infrastructure.persistance.jpa.repository.FavoriteTeamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamFavoriteCommandServiceImpl implements TeamFavoriteCommandService {
  private final FavoriteTeamRepository favoriteTeamRepository;
  private final ExternalUserManegementService externalUserManagementService;

  @Transactional
  @Override
  public void handle(RemoveFavoriteTeamCommand command) {
    FavoriteTeam favoriteTeam = favoriteTeamRepository.findByUserIdAndTeamId(command.getUserId(), command.getTeamId())
        .orElse(null);

    if (favoriteTeam == null) {
      log.error("[TeamFavoriteCommandServiceImpl.handle] Team not favorited");
      throw new RuntimeException("Team not favorited");
    }

    log.info("[TeamFavoriteCommandServiceImpl.handle] Team is favorited, removing");

    favoriteTeamRepository.delete(favoriteTeam);

    log.info("[TeamFavoriteCommandServiceImpl.handle] The team is now not favorited");
  }

  @Transactional
  @Override
  public void handle(AddFavoriteTeamCommand command) {
    if (!externalUserManagementService.existUser(command.getUserId())) {
      throw new RuntimeException("User not found");
    }
    log.info("[TeamFavoriteCommandServiceImpl.handle] User found");

    FavoriteTeam favoriteTeam = favoriteTeamRepository.findByUserIdAndTeamId(command.getUserId(), command.getTeamId())
        .orElse(null);
    if (favoriteTeam != null) {
      log.error("[TeamFavoriteCommandServiceImpl.handle] Team already favorited");
      throw new RuntimeException("Team already favorited");
    }

    log.info("[TeamFavoriteCommandServiceImpl.handle] Team is not favorited yet");

    favoriteTeam = new FavoriteTeam(command.getTeamId(), command.getUserId());
    favoriteTeamRepository.save(favoriteTeam);

    log.info("[TeamFavoriteCommandServiceImpl.handle] The team is now favorited");
  }

}
