package com.betalyze.dataretrieval.domain.service;

import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueMatches;
import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueTeams;

public interface DataRetrievalCommandService {
  void handle(FetchAndUpdateSeasonLeagueTeams command) throws Exception;

  void handle(FetchAndUpdateSeasonLeagueMatches command) throws Exception;
}
