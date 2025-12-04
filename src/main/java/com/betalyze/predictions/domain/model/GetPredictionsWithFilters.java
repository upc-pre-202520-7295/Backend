package com.betalyze.predictions.domain.model;

import java.time.LocalDate;

public record GetPredictionsWithFilters(
        String season,
        LocalDate startDate,
        LocalDate endDate
) {}