package com.betalyze.dataretrieval.interfaces.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.dataretrieval.domain.model.query.GetAllTeams;
import com.betalyze.dataretrieval.domain.model.query.GetTeamById;
import com.betalyze.dataretrieval.domain.service.DataRetrievalQueryService;
import com.betalyze.dataretrieval.interfaces.resources.TeamResource;
import com.betalyze.shared.application.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Teams", description = "Team data endpoints")
public class TeamController {

  private final DataRetrievalQueryService dataRetrievalQueryService;

  @GetMapping("/{teamId}")
  @Operation(summary = "Get team by id")
  public ResponseEntity<Response<TeamResource>> getTeamById(@PathVariable UUID teamId) {

    log.info("[TeamController.getTeamById] Get team by id: {}", teamId);

    Optional<Team> team = dataRetrievalQueryService.handle(new GetTeamById(teamId));

    log.info("[TeamController.getTeamById] team: {}", team);

    TeamResource resource = new TeamResource(team.get().getId(), team.get().getTeamName(),
        team.get().getTeamImageUrl());

    log.info("[TeamController.getTeamById] resource: {}", resource);

    return ResponseEntity.ok(Response.success(resource));
  }

  @GetMapping("")
  @Operation(summary = "Get all teams")
  public ResponseEntity<Response<List<TeamResource>>> getAllTeams() {
    log.info("[TeamController.getAllTeams] Get all teams");

    List<Team> teams = dataRetrievalQueryService.handle(new GetAllTeams());

    log.info("[TeamController.getAllTeams] teams: {}", teams);

    List<TeamResource> resources = teams.stream().map(t -> new TeamResource(t.getId(), t.getTeamName(),
        t.getTeamImageUrl())).toList();

    log.info("[TeamController.getAllTeams] resources: {}", resources);

    return ResponseEntity.ok(Response.success(resources));
  }
}
