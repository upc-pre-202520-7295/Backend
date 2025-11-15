package com.betalyze.dataretrieval.domain.model.model.aggregate;

import java.time.LocalDateTime;

import com.betalyze.dataretrieval.domain.model.model.entity.MatchDetails;
import com.betalyze.dataretrieval.domain.model.model.entity.Team;
import com.betalyze.shared.domain.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
public class Match extends Entity {
  @Column(name = "external_id", unique = true, nullable = false)
  private String externalId;

  @Column(name = "match_date", nullable = false)
  private LocalDateTime matchDate;

  @Column(name = "external_provider", nullable = false)
  private String externalProvider;

  @Column(name = "league_external_id", nullable = false)
  private String leagueExternalId;

  @Column(name = "league_name", nullable = false)
  private String leagueName;

  @Column(name = "season", nullable = false)
  private String season;

  @OneToOne(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
  private MatchDetails details;

  @ManyToOne
  @JoinColumn(name = "home_team_id", nullable = false)
  private Team homeTeam;

  @ManyToOne
  @JoinColumn(name = "away_team_id", nullable = false)
  private Team awayTeam;

  public Match(String externalId, LocalDateTime matchDate, String leagueName, String leagueExternalId,
      String externalProvider, String season) {
    this.externalId = externalId;
    this.matchDate = matchDate;
    this.leagueName = leagueName;
    this.leagueExternalId = leagueExternalId;
    this.externalProvider = externalProvider;
    this.season = season;
  }
}
