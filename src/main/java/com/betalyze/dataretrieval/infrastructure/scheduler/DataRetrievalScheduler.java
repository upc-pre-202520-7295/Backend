// language: java
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

        private static final List<Integer> LEAGUES_LIST = List.of(
            2,   // UEFA Champions League
            3,   // UEFA Europa League
            39,  // Premier League (Inglaterra)
            140, // La Liga (España)
            135, // Serie A (Italia)
            78,  // Bundesliga (Alemania)
            61   // Ligue 1 (Francia)
        );

        @Scheduled(cron = "0 0 6 * * MON") // Every monday at 6 AM
        public void checkForUpdateSeasonLeagueSchedule() {
          List<Integer> seasons = List.of(2023);
          for (Integer season : seasons) {
            for (Integer leagueId : LEAGUES_LIST) {
              try {
                log.info(
                    "[DataRetrievalScheduler.CheckForUpdateSeasonLeagueSchedule] Checking for updates to season {} matches for league {}",
                    season, leagueId);

                dataRetrievalCommandServiceImpl.handle(new FetchAndUpdateSeasonLeagueTeams(season, leagueId));
                dataRetrievalCommandServiceImpl.handle(new FetchAndUpdateSeasonLeagueMatches(season, leagueId));

              } catch (Exception e) {
                log.error("[DataRetrievalScheduler.CheckForUpdateSeasonLeagueSchedule] Error in season {} league {} match retrieval",
                    season, leagueId, e);
              }
            }
          }
        }

      }