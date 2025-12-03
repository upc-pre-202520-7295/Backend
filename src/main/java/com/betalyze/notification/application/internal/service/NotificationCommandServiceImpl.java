package com.betalyze.notification.application.internal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.notification.domain.model.agreggate.UserNotification;
import com.betalyze.notification.domain.model.command.EnsureUserNotificationExists;
import com.betalyze.notification.domain.model.command.MarkAsReadCommand;
import com.betalyze.notification.domain.model.command.SendActiveNotificacionsCommand;
import com.betalyze.notification.domain.model.command.SendTestNotification;
import com.betalyze.notification.domain.model.command.UpdateFcmTokenCommand;
import com.betalyze.notification.domain.model.entity.Notification;
import com.betalyze.notification.domain.service.NotificationCommandService;
import com.betalyze.notification.infrastructure.firebase.FirebaseNotificationService;
import com.betalyze.notification.infrastructure.persistance.jpa.repository.NotificationRepository;
import com.betalyze.notification.infrastructure.persistance.jpa.repository.UserNotificastionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService {

  private final NotificationRepository notificationRepository;
  private final UserNotificastionRepository userNotificationRepository;
  private final FirebaseNotificationService firebaseService;

  @Transactional
  @Override
  public void handle(SendActiveNotificacionsCommand command) {
    List<Notification> pendingNotifications = notificationRepository.findBySentFalse();

   // log.info("Sending {} pending notifications", pendingNotifications.size());

    for (Notification notification : pendingNotifications) {
      try {
        String fcmToken = notification.getUserNotification().getFcmToken();

        String messageId = firebaseService.sendNotification(fcmToken, notification.getTitle(),
            notification.getMessage(), notification.getImageUrl());

        notification.markAsSent(messageId);
        notificationRepository.save(notification);

      } catch (Exception e) {
        log.error("Error sending notification {}", notification.getId(), e);
      }
    }
  }

  @Transactional
  @Override
  public void handle(SendTestNotification command) {
    log.info("[NotificationCommandServiceImpl.handle] Sending test notification");

    UserNotification user = userNotificationRepository.findByUserId(command.getUserId()).orElseThrow(
        () -> new RuntimeException("User not found"));

    log.info("[NotificationCommandServiceImpl.handle] User found");

    Notification notification = new Notification(command.getTitle(), command.getMessage(), command.getImageUrl(), "");
    notification.setUserNotification(user);

    log.info("[NotificationCommandServiceImpl.handle] Notification created");

    notificationRepository.save(notification);
    log.info("[NotificationCommandServiceImpl.handle] Notification saved");
  }

  // @Transactional
  // @Override
  // public void handle(CreateMatchNotificationCommand command) {
  // List<UUID> todayMatches = List.of();
  //
  // for (UUID matchId : todayMatches) {
  // _checkMatchNotifications(command.getUserId(), matchId);
  // }
  // }
  //
  // private void _checkMatchNotifications(UUID userId, UUID matchId) {
  // // Check for favorite teams
  // userManagementACL.getFavoriteHomeTeamIds(email).stream()
  // .forEach(ft -> _createMatchNotification(email, matchId, ft, true));
  //
  // userManagementACL.getFavoriteAwayTeamIds(email).stream()
  // .forEach(ft -> _createMatchNotification(email, matchId, ft, false));
  // }
  //
  // private void _createMatchNotification(UUID userId, UUID matchId, UUID
  // favoriteTeamId, boolean isHomeTeam) {
  // UserReadDevice user =
  // userReadDeviceRepository.findByUserId(userId).orElse(null);
  //
  // if (user == null) {
  // log.warn("User not found for userId {}", userId);
  // return;
  // }
  //
  // String title = "Match Scheduled: " + favoriteTeamId;
  // String message = String.format("%s vs %s on %s",
  // matchId,
  // isHomeTeam,
  // favoriteTeamId);
  //
  // Notification notification = new Notification(userId, title, message);
  //
  // notificationRepository.save(notification);
  // log.info("Created match notification for user {} and team {}", userId,
  // favoriteTeamId);
  // }

  @Override
  @Transactional
  public void handle(MarkAsReadCommand command) {
    Notification notification = notificationRepository.findById(command.getNotificationId())
        .orElseThrow(() -> new RuntimeException("Notification not found"));

    notification.markAsRead();
    notificationRepository.save(notification);
  }

  @Override
  @Transactional
  public void handle(UpdateFcmTokenCommand command) {
    UserNotification user = userNotificationRepository.findByUserId(command.getUserId()).orElse(null);

    if (user == null) {
      log.warn("User not found for userId {}", command.getUserId());
      return;
    }

    user.setFcmToken(command.getFcmToken());
  }

  @Override
  @Transactional
  public void handle(EnsureUserNotificationExists command) {
    UserNotification user = userNotificationRepository.findByUserId(command.getUserId()).orElse(null);

    if (user == null) {
      user = new UserNotification(command.getUserId(), "");
      userNotificationRepository.save(user);

      log.info("Created user notification for user {}", command.getUserId());
    }
  }
}
