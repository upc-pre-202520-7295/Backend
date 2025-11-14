package com.betalyze.usermanagement.domain.model.query;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserProfileQuery {
  private UUID userId;
}
