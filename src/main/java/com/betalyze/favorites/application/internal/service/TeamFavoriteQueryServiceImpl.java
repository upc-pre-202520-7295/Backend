package com.betalyze.favorites.application.internal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.favorites.domain.model.aggregate.FavoriteTeam;
import com.betalyze.favorites.domain.model.query.GetFavoriteTeamsQuery;
import com.betalyze.favorites.domain.service.TeamFavoriteQueryService;
import com.betalyze.favorites.infrastructure.persistance.jpa.repository.FavoriteTeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamFavoriteQueryServiceImpl implements TeamFavoriteQueryService {

  private final FavoriteTeamRepository favoriteTeamRepository;

  @Transactional(readOnly = true)
  public List<FavoriteTeam> handle(GetFavoriteTeamsQuery getFavoriteTeamsQuery) {
    return favoriteTeamRepository.findByUserId(getFavoriteTeamsQuery.getUserId());
  }
}
