package com.betalyze.dataretrieval.application.internal.outboundservices.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalMatchDto {
  private String externalId;

  private String homeTeamExternalId;
  private String homeTeamName;
  private String homeTeamImageUrl;

  private String awayTeamExternalId;
  private String awayTeamName;
  private String awayTeamImageUrl;

  private LocalDateTime matchDate;

  private String provider;

  private String leagueExternalId;
  private String leagueImageUrl;
  private String leagueName;

  private String status;

  private Integer homeScore;
  private Integer awayScore;
}
