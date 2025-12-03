package com.betalyze.dataretrieval.application.internal.outboundservices.matches;

import java.util.List;

import com.betalyze.dataretrieval.application.internal.outboundservices.dto.ExternalMatchDto;

public interface ExternalMatchServices {
  List<ExternalMatchDto> getMatchesPerSeason(Integer season, Integer leagueId);
}
