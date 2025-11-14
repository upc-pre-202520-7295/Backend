package com.betalyze.notification.domain.model.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendTestNotification {
  private UUID userId;
  private String title;
  private String message;
  private String imageUrl;
}
