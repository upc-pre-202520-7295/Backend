package com.betalyze.shared.domain;

import java.time.LocalDateTime;

public interface DomainEvent {
  LocalDateTime occurredOn();
}
