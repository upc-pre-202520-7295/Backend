package com.betalyze.favorites.infrastructure.resources;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteTeamResource {
  private UUID teamId;
  private UUID userId;
  private String teamName;
  private String teamImageUrl;
}
