package com.betalyze.dataretrieval.application.internal.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.dataretrieval.domain.model.query.GetAllMatches;
import com.betalyze.dataretrieval.domain.model.query.GetAllTeams;
import com.betalyze.dataretrieval.domain.model.query.GetTeamById;
import com.betalyze.dataretrieval.domain.model.query.GetTodayMatches;
import com.betalyze.dataretrieval.domain.service.DataRetrievalQueryService;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.MatchRepository;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.TeamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataRetrievalQueryServiceImpl implements DataRetrievalQueryService {
  private final MatchRepository matchRepository;
  private final TeamRepository teamRepository;

  @Transactional(readOnly = true)
  @Override
  public List<Match> handle(GetTodayMatches query) {
    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
    LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

    return matchRepository.findByMatchDateBetweenOrderByMatchDate(startOfDay, endOfDay);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Match> handle(GetAllMatches query) {
    return matchRepository.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<Team> handle(GetTeamById query) {
    return teamRepository.findById(query.getTeamId());
  }

  @Transactional(readOnly = true)
  @Override
  public List<Team> handle(GetAllTeams query) {
    return teamRepository.findAll();
  }
}
