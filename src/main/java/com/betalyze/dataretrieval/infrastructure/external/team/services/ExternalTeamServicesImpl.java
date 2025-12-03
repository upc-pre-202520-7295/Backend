package com.betalyze.dataretrieval.infrastructure.external.team.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.betalyze.dataretrieval.application.internal.outboundservices.dto.ExternalTeamDto;
import com.betalyze.dataretrieval.application.internal.outboundservices.teams.ExternalTeamServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalTeamServicesImpl implements ExternalTeamServices {
  private final String apiHost = "https://v3.football.api-sports.io/teams/";
  private final String apiKey = "263b946926d619781746d75130d449f8"; // TODO: pass to env

  RestClient restClient = RestClient.create();

  @Override
  public List<ExternalTeamDto> getTeamsPerSeason(Integer season, Integer leagueId) {
    String jsonStr = restClient
        .get()
        .uri(apiHost + "?league=" + leagueId + "&season=" + season)
        .header("x-apisports-key", apiKey)
        .retrieve().body(String.class);

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode jsonNode = objectMapper.readTree(jsonStr);
      JsonNode response = jsonNode.get("response");

      List<ExternalTeamDto> teams = new ArrayList<>();
      for (JsonNode info : response) {
        JsonNode team = info.get("team");
        teams.add(new ExternalTeamDto(
            team.get("id").asText(),
            team.get("name").asText(),
            team.get("logo").asText(),
            leagueId.toString()));
      }

      return teams;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
