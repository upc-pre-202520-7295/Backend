package com.betalyze.dataretrieval.domain.model.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.betalyze.dataretrieval.domain.model.model.aggregate.Match;
import com.betalyze.shared.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
public class Team extends Entity {
  @Column(name = "team_external_id", nullable = false)
  private String teamExternalId;

  @Column(name = "team_name", nullable = false)
  private String teamName;

  @Column(name = "team_image_url", nullable = false)
  private String teamImageUrl;

  @Column(name = "league_external_id", nullable = false)
  private String leagueExternalId;

  @Column(name = "season", nullable = false)
  private String season;

  @Column(nullable = false, unique = true)
  private Long code;

  @OneToMany(mappedBy = "homeTeam")
  private List<Match> homeMatches = new ArrayList<>();

  @OneToMany(mappedBy = "awayTeam")
  private List<Match> awayMatches = new ArrayList<>();

  public Team(String teamExternalId, String teamName, String teamImageUrl, String leagueExternalId, String season) {
    this.teamExternalId = teamExternalId;
    this.teamName = teamName;
    this.teamImageUrl = teamImageUrl;
    this.leagueExternalId = leagueExternalId;
    this.season = season;
  }
}
