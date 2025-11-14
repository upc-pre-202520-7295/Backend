package com.betalyze.notification.domain.model.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnsureUserNotificationExists {
  private UUID userId;
}
