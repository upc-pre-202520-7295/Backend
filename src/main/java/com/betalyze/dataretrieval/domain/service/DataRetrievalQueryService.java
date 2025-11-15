package com.betalyze.dataretrieval.domain.service;

import java.util.List;
import java.util.Optional;

import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.dataretrieval.domain.model.query.GetAllMatches;
import com.betalyze.dataretrieval.domain.model.query.GetAllTeams;
import com.betalyze.dataretrieval.domain.model.query.GetTeamById;
import com.betalyze.dataretrieval.domain.model.query.GetTodayMatches;

public interface DataRetrievalQueryService {
  List<Match> handle(GetAllMatches query);

  List<Match> handle(GetTodayMatches query);

  Optional<Team> handle(GetTeamById query);

  List<Team> handle(GetAllTeams query);
}
