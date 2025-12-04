package com.betalyze.predictions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "external_provider", nullable = false)
    private String externalProvider;

    @Column(name = "league_external_id", nullable = false)
    private String leagueExternalId;

    @Column(name = "league_name", nullable = false)
    private String leagueName;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Column(name = "season", nullable = false)
    private String season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private TeamEntity homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private TeamEntity awayTeam;
}
