package com.betalyze.notification.domain.service;

import com.betalyze.notification.domain.model.command.EnsureUserNotificationExists;
import com.betalyze.notification.domain.model.command.MarkAsReadCommand;
import com.betalyze.notification.domain.model.command.SendActiveNotificacionsCommand;
import com.betalyze.notification.domain.model.command.SendTestNotification;
import com.betalyze.notification.domain.model.command.UpdateFcmTokenCommand;

public interface NotificationCommandService {
  void handle(SendActiveNotificacionsCommand command);

  // void handle(CreateMatchNotificationCommand command);

  void handle(MarkAsReadCommand command);

  void handle(UpdateFcmTokenCommand command);

  void handle(EnsureUserNotificationExists command);

  void handle(SendTestNotification command);
}
