package com.betalyze.notification.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.betalyze.notification.domain.model.command.SendActiveNotificacionsCommand;
import com.betalyze.notification.domain.service.NotificationCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

  private final NotificationCommandService notificationCommandService;

  @Scheduled(fixedRate = 3000) // Every 5 minutes
  public void sendPendingNotifications() {
    try {
      notificationCommandService.handle(new SendActiveNotificacionsCommand());
    } catch (Exception e) {
      log.error("Error sending pending notifications", e);
    }
  }
}
