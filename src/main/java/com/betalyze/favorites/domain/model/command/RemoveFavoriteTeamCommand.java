package com.betalyze.favorites.domain.model.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveFavoriteTeamCommand {
  private UUID userId;
  private UUID teamId;
}
