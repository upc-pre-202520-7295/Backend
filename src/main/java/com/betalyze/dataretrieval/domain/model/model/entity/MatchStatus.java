package com.betalyze.dataretrieval.domain.model.model.entity;

public enum MatchStatus {
  SCHEDULED, // Partido programado, aún no empezó
  IN_PLAY, // Partido en juego
  FINISHED, // Partido finalizado
  POSTPONED, // Partido aplazado
  CANCELLED, // Partido cancelado
  ABANDONED, // Partido abandonado
  NOT_PLAYED // Victoria por forfait o pérdida técnica
}
