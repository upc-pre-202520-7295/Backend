package com.betalyze.favorites.domain.service;

import java.util.List;

import com.betalyze.favorites.domain.model.aggregate.FavoriteTeam;
import com.betalyze.favorites.domain.model.query.GetFavoriteTeamsQuery;

public interface TeamFavoriteQueryService {
  List<FavoriteTeam> handle(GetFavoriteTeamsQuery getFavoriteTeamsQuery);
}
