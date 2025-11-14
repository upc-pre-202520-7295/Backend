package com.betalyze.favorites.domain.model.aggregate;

import java.util.UUID;

import com.betalyze.shared.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
public class FavoriteTeam extends Entity {
  @Column(nullable = false, insertable = true)
  private UUID teamId;

  @Column(nullable = false, insertable = true)
  private UUID userId;

  public FavoriteTeam(UUID teamId, UUID userId) {
    this.teamId = teamId;
    this.userId = userId;
  }
}
