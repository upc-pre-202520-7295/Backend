package com.betalyze.dataretrieval.domain.model.query;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTeamById {
  private UUID teamId;
}
