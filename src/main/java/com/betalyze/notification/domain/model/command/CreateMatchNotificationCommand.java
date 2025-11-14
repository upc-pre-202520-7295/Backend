package com.betalyze.notification.domain.model.command;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMatchNotificationCommand {
  private UUID userId;
  private Date searchDate;
}
