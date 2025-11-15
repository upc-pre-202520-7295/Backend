package com.betalyze.dataretrieval.application.internal.outboundservices.teams;

import java.util.List;

import com.betalyze.dataretrieval.application.internal.outboundservices.dto.ExternalTeamDto;

public interface ExternalTeamServices {
  List<ExternalTeamDto> getTeamsPerSeason(Integer season);
}
