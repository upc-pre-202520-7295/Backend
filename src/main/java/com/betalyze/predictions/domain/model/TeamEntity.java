package com.betalyze.predictions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "code", nullable = false, unique = true)
    private Long code;

    @Column(name = "league_external_id", nullable = false)
    private String leagueExternalId;

    @Column(name = "season", nullable = false)
    private String season;

    @Column(name = "team_external_id", nullable = false)
    private String teamExternalId;

    @Column(name = "team_image_url", nullable = false)
    private String teamImageUrl;

    @Column(name = "team_name", nullable = false)
    private String teamName;
}
