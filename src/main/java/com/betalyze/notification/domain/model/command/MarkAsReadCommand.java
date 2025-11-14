package com.betalyze.notification.domain.model.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarkAsReadCommand {
  private UUID userId;
  private UUID notificationId;
}
