package com.betalyze.dataretrieval.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResource {
  private String matchId;
  private TeamResource homeTeam;
  private TeamResource awayTeam;
  private Integer homeScore;
  private Integer awayScore;
  private String matchDate;
  private String status;
}
