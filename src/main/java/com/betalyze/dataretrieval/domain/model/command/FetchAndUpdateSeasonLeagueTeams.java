package com.betalyze.dataretrieval.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchAndUpdateSeasonLeagueTeams {
  private Integer season;
}
