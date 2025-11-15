package com.betalyze.dataretrieval.domain.model.model.entity;

import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.shared.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "match_details")
@Getter
@Setter
@NoArgsConstructor
public class MatchDetails extends Entity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "match_id")
  private Match match;

  @Column(name = "home_team_score", nullable = false)
  private Integer homeTeamScore = 0;

  @Column(name = "away_team_score", nullable = false)
  private Integer awayTeamScore = 0;

  @Enumerated(EnumType.STRING)
  @Column(name = "match_status", nullable = false)
  private MatchStatus matchStatus = MatchStatus.SCHEDULED;

  public MatchDetails(Integer homeTeamScore, Integer awayTeamScore, MatchStatus matchStatus) {
    this.homeTeamScore = homeTeamScore;
    this.awayTeamScore = awayTeamScore;
    this.matchStatus = matchStatus;
  }
}
