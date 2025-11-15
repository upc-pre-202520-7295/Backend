package com.betalyze.dataretrieval.application.internal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.dataretrieval.application.internal.outboundservices.dto.ExternalMatchDto;
import com.betalyze.dataretrieval.application.internal.outboundservices.dto.ExternalTeamDto;
import com.betalyze.dataretrieval.application.internal.outboundservices.matches.ExternalMatchServices;
import com.betalyze.dataretrieval.application.internal.outboundservices.teams.ExternalTeamServices;
import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueMatches;
import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueTeams;
import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.dataretrieval.domain.model.model.entity.MatchDetails;
import com.betalyze.dataretrieval.domain.model.model.entity.MatchStatus;
import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.dataretrieval.domain.service.DataRetrievalCommandService;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.MatchRepository;
import com.betalyze.dataretrieval.infrastructure.persistance.jpa.repository.TeamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataRetrievalCommandServiceImpl implements DataRetrievalCommandService {
  private final TeamRepository teamRepository;
  private final MatchRepository matchRepository;
  private final ExternalMatchServices externalMatchServices;
  private final ExternalTeamServices externalTeamServices;

  @Transactional
  @Override
  public void handle(FetchAndUpdateSeasonLeagueTeams command) throws Exception {
    log.info(
        "[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueTeams] Checking for updates to the season league schedule");

    List<ExternalTeamDto> resTeams = externalTeamServices.getTeamsPerSeason(command.getSeason());

    log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueTeams] resTeams: {}", resTeams);

    for (ExternalTeamDto resTeam : resTeams) {
      Optional<Team> team = teamRepository.findByTeamExternalId(resTeam.getExternalId());

      if (team.isEmpty()) {
        Long code = teamRepository.count();

        Team teamToSave = new Team(resTeam.getExternalId(), resTeam.getTeamName(),
            resTeam.getTeamImageUrl(), resTeam.getLeagueExternalId(), command.getSeason().toString());

        teamToSave.setCode(code);

        teamRepository.save(teamToSave);
        log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueTeams] Adding team {}",
            resTeam.getTeamName());

        continue;
      }

      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueTeams] team: {}", team);

      Team teamToUpdate = team.get();
      teamToUpdate.setTeamName(resTeam.getTeamName());
      teamToUpdate.setTeamImageUrl(resTeam.getTeamImageUrl());
      teamToUpdate.setLeagueExternalId(resTeam.getLeagueExternalId());
      teamToUpdate.setSeason(command.getSeason().toString());

      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueTeams] Updating team {}",
          resTeam.getTeamName());
      teamRepository.save(teamToUpdate);

      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueTeams] Updated team {}",
          resTeam.getTeamName());
    }
  }

  @Transactional
  @Override
  public void handle(FetchAndUpdateSeasonLeagueMatches command) throws Exception {
    log.info(
        "[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] Checking for updates to the season league schedule");

    List<ExternalMatchDto> resMatches = externalMatchServices.getMatchesPerSeason(command.getSeason());

    log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] resMatches: {}", resMatches);

    for (ExternalMatchDto resMatch : resMatches) {
      Match match = matchRepository.findByExternalId(resMatch.getExternalId()).orElse(new Match(
          resMatch.getExternalId(),
          resMatch.getMatchDate(),
          resMatch.getLeagueName(),
          resMatch.getLeagueExternalId(),
          resMatch.getProvider(),
          command.getSeason().toString()));

      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] Adding match {} VS {} - {}",
          resMatch.getHomeTeamName(), resMatch.getAwayTeamName(), resMatch.getMatchDate());

      Optional<Team> homeTeamOpt = teamRepository.findByTeamExternalId(resMatch.getHomeTeamExternalId());
      Optional<Team> awayTeamOpt = teamRepository.findByTeamExternalId(resMatch.getAwayTeamExternalId());

      if (homeTeamOpt.isEmpty() || awayTeamOpt.isEmpty()) {
        log.error(
            "[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] One or both teams not found for match {} vs {}, skipping",
            resMatch.getHomeTeamName(), resMatch.getAwayTeamName());
        continue;
      }

      Team homeTeam = homeTeamOpt.get();
      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] Home team: {}",
          homeTeam.getTeamName());

      Team awayTeam = awayTeamOpt.get();
      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] Away team: {}",
          awayTeam.getTeamName());

      match.setHomeTeam(homeTeam);
      match.setAwayTeam(awayTeam);

      MatchStatus matchStatus = mapFromString(resMatch.getStatus());

      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] Match status: {}", matchStatus);

      MatchDetails matchDetails = new MatchDetails(
          resMatch.getHomeScore(),
          resMatch.getAwayScore(),
          matchStatus);

      match.setDetails(matchDetails);
      matchDetails.setMatch(match);
      matchRepository.save(match);

      log.info("[DataRetrievalCommandServiceImpl.FetchAndUpdateSeasonLeagueMatches] Updated match {} VS {} - {}",
          resMatch.getHomeTeamName(), resMatch.getAwayTeamName(), resMatch.getMatchDate());
    }
  }

  private MatchStatus mapFromString(String status) {
    log.info("[DataRetrievalCommandServiceImpl.mapFromString] Mapping status {}", status);
    switch (status.toUpperCase()) {
      case "TBD":
      case "NS":
        return MatchStatus.SCHEDULED;
      case "1H":
      case "HT":
      case "2H":
      case "ET":
      case "BT":
      case "P":
      case "INT":
      case "LIVE":
      case "SUSP":
        return MatchStatus.IN_PLAY;
      case "FT":
      case "AET":
      case "PEN":
        return MatchStatus.FINISHED;
      case "PST":
        return MatchStatus.POSTPONED;
      case "CANC":
        return MatchStatus.CANCELLED;
      case "ABD":
        return MatchStatus.ABANDONED;
      case "AWD":
      case "WO":
        return MatchStatus.NOT_PLAYED;
      default:
        return MatchStatus.FINISHED;
    }
  }
}
