package com.betalyze.dataretrieval.interfaces.acl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.dataretrieval.domain.model.query.GetTeamById;
import com.betalyze.dataretrieval.domain.service.DataRetrievalQueryService;
import com.betalyze.dataretrieval.interfaces.resources.TeamResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataRetrievalContextFacade {

  private final DataRetrievalQueryService dataRetrievalQueryService;

  public Optional<TeamResource> getTeamById(UUID teamId) {
    Optional<Team> team = dataRetrievalQueryService.handle(new GetTeamById(teamId));

    if (team.isEmpty()) {
      log.error("Team not found for teamId {}", teamId);
      return Optional.empty();
    }

    return Optional.of(new TeamResource(team.get().getId(), team.get().getTeamName(), team.get().getTeamImageUrl()));
  }
}
