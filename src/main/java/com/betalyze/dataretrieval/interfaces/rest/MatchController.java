package com.betalyze.dataretrieval.interfaces.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueMatches;
import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueTeams;
import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.dataretrieval.domain.model.query.GetAllMatches;
import com.betalyze.dataretrieval.domain.model.query.GetTodayMatches;
import com.betalyze.dataretrieval.domain.service.DataRetrievalCommandService;
import com.betalyze.dataretrieval.domain.service.DataRetrievalQueryService;
import com.betalyze.dataretrieval.interfaces.resources.MatchResource;
import com.betalyze.dataretrieval.interfaces.resources.TeamResource;
import com.betalyze.shared.application.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/matches")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Matches", description = "Match data endpoints")
public class MatchController {

  private final DataRetrievalQueryService dataRetrievalQueryService;
  private final DataRetrievalCommandService dataRetrievalCommandService;

  @GetMapping("/today")
  @Operation(summary = "Get today's matches")
  public ResponseEntity<Response<List<MatchResource>>> getTodayMatches() {
    try {

      log.info("[MatchController.getTodayMatches] Get today's matches");
      List<Match> matches = dataRetrievalQueryService.handle(new GetTodayMatches(LocalDate.now()));

      log.info("[MatchController.getTodayMatches] matches: {}", matches);
      List<MatchResource> resources = matches.stream().map(m -> new MatchResource(
          m.getId().toString(),
          new TeamResource(m.getHomeTeam().getId(), m.getHomeTeam().getTeamName(), m.getHomeTeam().getTeamImageUrl()),
          new TeamResource(m.getAwayTeam().getId(), m.getAwayTeam().getTeamName(), m.getAwayTeam().getTeamImageUrl()),
          m.getDetails().getHomeTeamScore(),
          m.getDetails().getAwayTeamScore(),
          m.getMatchDate().toString(), m.getDetails().getMatchStatus().toString())).toList();

      log.info("[MatchController.getTodayMatches] resources: {}", resources);
      return ResponseEntity.ok(Response.success(resources));
    } catch (Exception e) {
      log.error("[MatchController.getTodayMatches] Error getting today's matches", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/")
  @Operation(summary = "Get all matches")
  public ResponseEntity<Response<List<MatchResource>>> getAllMatches() {
    try {
      log.info("[MatchController.getAllMatches] Get all matches");
      List<Match> matches = dataRetrievalQueryService.handle(new GetAllMatches());

      log.info("[MatchController.getAllMatches] matches: {}", matches);
      List<MatchResource> resources = matches.stream().map(m -> new MatchResource(
          m.getId().toString(),
          new TeamResource(m.getHomeTeam().getId(), m.getHomeTeam().getTeamName(), m.getHomeTeam().getTeamImageUrl()),
          new TeamResource(m.getAwayTeam().getId(), m.getAwayTeam().getTeamName(), m.getAwayTeam().getTeamImageUrl()),
          m.getDetails().getHomeTeamScore(),
          m.getDetails().getAwayTeamScore(),
          m.getMatchDate().toString(),
          m.getDetails().getMatchStatus().toString())).toList();

      log.info("[MatchController.getAllMatches] resources: {}", resources);
      return ResponseEntity.ok(Response.success(resources));
    } catch (Exception e) {
      log.error("[MatchController.getAllMatches] Error getting all matches", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  // @GetMapping("/{matchId}/odds")
  // @Operation(summary = "Get match odds")
  // public ResponseEntity<Response<List<MatchOddsDto>>>
  // getMatchOdds(@PathVariable String matchId) {
  // List<MatchOddsDto> odds = dataRetrievalService.getMatchOdds(matchId);
  // return ResponseEntity.ok(Response.success(odds));
  // }

  // @GetMapping("/teams/{teamId}/statistics")
  // @Operation(summary = "Get team statistics")
  // public ResponseEntity<Response<TeamStatisticsDto>>
  // getTeamStatistics(@PathVariable String teamId) {
  // TeamStatisticsDto statistics =
  // dataRetrievalService.getTeamStatistics(teamId);
  // return ResponseEntity.ok(Response.success(statistics));
  // }

  @PostMapping("/refresh/{season}")
  @Operation(summary = "Manually refresh today's matches")
  public ResponseEntity<Response<Void>> refreshMatches(@PathVariable Integer season) throws Exception {
    try {
      log.info("[MatchController.refreshMatches] Refreshing Teams for season {}", season);
      dataRetrievalCommandService.handle(new FetchAndUpdateSeasonLeagueTeams(season));

      log.info("[MatchController.refreshMatches] Refreshing Matches for season {}", season);
      dataRetrievalCommandService.handle(new FetchAndUpdateSeasonLeagueMatches(season));

      log.info("[MatchController.refreshMatches] Matches refreshed successfully");
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("[MatchController.refreshMatches] Error refreshing matches", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
