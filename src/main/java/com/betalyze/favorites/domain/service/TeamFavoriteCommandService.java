package com.betalyze.favorites.domain.service;

import com.betalyze.favorites.domain.model.command.AddFavoriteTeamCommand;
import com.betalyze.favorites.domain.model.command.RemoveFavoriteTeamCommand;

public interface TeamFavoriteCommandService {
  void handle(RemoveFavoriteTeamCommand command);

  void handle(AddFavoriteTeamCommand command);
}
