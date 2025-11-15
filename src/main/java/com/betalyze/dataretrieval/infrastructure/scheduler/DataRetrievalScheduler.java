package com.betalyze.dataretrieval.infrastructure.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.betalyze.dataretrieval.application.internal.service.DataRetrievalCommandServiceImpl;
import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueMatches;
import com.betalyze.dataretrieval.domain.model.command.FetchAndUpdateSeasonLeagueTeams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataRetrievalScheduler {

  private final DataRetrievalCommandServiceImpl dataRetrievalCommandServiceImpl;

  @Scheduled(cron = "0 0 6 * * MON") // Every monday at 6 AM
  public void checkForUpdateSeasonLeagueSchedule() {
    List<Integer> seasons = List.of(2023);
    for (Integer season : seasons) {
      try {
        log.info(
            "[DataRetrievalScheduler.CheckForUpdateSeasonLeagueSchedule] Checking for updates to season {} matches",
            season);

        dataRetrievalCommandServiceImpl.handle(new FetchAndUpdateSeasonLeagueTeams(season));
        dataRetrievalCommandServiceImpl.handle(new FetchAndUpdateSeasonLeagueMatches(season));

      } catch (Exception e) {
        log.error("[DataRetrievalScheduler.CheckForUpdateSeasonLeagueSchedule] Error in season {} match retrieval",
            season, e);
      }
    }
  }

}
