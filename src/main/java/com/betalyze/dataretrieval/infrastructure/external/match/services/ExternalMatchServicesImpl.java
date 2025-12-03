package com.betalyze.dataretrieval.infrastructure.external.match.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.betalyze.dataretrieval.application.internal.outboundservices.dto.ExternalMatchDto;
import com.betalyze.dataretrieval.application.internal.outboundservices.matches.ExternalMatchServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalMatchServicesImpl implements ExternalMatchServices {
  private final String apiHost = "https://v3.football.api-sports.io/fixtures";
  private final String apiKey = "263b946926d619781746d75130d449f8";
  private final String leagueId = "2";

  RestClient restClient = RestClient.create();

  public List<ExternalMatchDto> getMatchesPerSeason(Integer season) {
    final String from = season + "-01-01";
    final String to = season + "-12-31";

    String jsonStr = restClient
        .get()
           // .uri(apiHost + "?league=" + leagueId + "&season=" + seaso
        .uri(apiHost + "?league=" + leagueId + "&season=" + season + "&from=" + from  + "&to=" +to)
        .header("x-apisports-key", apiKey)
        .retrieve().body(String.class);

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode jsonNode = objectMapper.readTree(jsonStr);
      JsonNode response = jsonNode.get("response");

      List<ExternalMatchDto> matches = new ArrayList<>();
      for (JsonNode info : response) {
        JsonNode fixture = info.get("fixture");
        JsonNode teams = info.get("teams");
        JsonNode league = info.get("league");
        JsonNode goals = info.get("goals");

        JsonNode status = fixture.get("status");

        JsonNode homeTeam = teams.get("home");
        JsonNode awayTeam = teams.get("away");

        OffsetDateTime matchDate = OffsetDateTime.parse(fixture.get("date").asText());
        LocalDateTime localDateTime = matchDate.toLocalDateTime();

        ExternalMatchDto match = new ExternalMatchDto(
            fixture.get("id").asText(),

            // teams
            homeTeam.get("id").asText(),
            homeTeam.get("name").asText(),
            homeTeam.get("logo").asText(),

            awayTeam.get("id").asText(),
            awayTeam.get("name").asText(),
            awayTeam.get("logo").asText(),

            localDateTime,
            "football-api-sports",

            league.get("id").asText(),
            league.get("logo").asText(),
            league.get("name").asText(),

            status.get("short").asText(),

            goals.get("home").asInt(),
            goals.get("away").asInt());

        matches.add(match);
      }

      return matches;

    } catch (Exception e) {
      log.error("Error parsing JSON from football API: {}", e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
