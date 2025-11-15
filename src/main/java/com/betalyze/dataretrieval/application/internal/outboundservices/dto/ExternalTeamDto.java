package com.betalyze.dataretrieval.application.internal.outboundservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalTeamDto {
  private String externalId;
  private String teamName;
  private String teamImageUrl;
  private String leagueExternalId;
}
