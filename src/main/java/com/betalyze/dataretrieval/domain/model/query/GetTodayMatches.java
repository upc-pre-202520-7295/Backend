package com.betalyze.dataretrieval.domain.model.query;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTodayMatches {
  private LocalDate today;
}
