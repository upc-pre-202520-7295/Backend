package com.betalyze.notification.domain.model.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateFcmTokenCommand {
  private UUID userId;
  private String fcmToken;
}
