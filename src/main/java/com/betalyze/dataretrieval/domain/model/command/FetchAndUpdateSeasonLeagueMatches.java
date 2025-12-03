package com.betalyze.dataretrieval.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FetchAndUpdateSeasonLeagueMatches {
  private final Integer season;
  private final Integer leagueId;
}
