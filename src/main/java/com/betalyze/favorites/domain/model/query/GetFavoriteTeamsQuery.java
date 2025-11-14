package com.betalyze.favorites.domain.model.query;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetFavoriteTeamsQuery {
  UUID userId;
}
