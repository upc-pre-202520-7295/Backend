package com.betalyze.notification.application.internal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.notification.domain.model.agreggate.UserNotification;
import com.betalyze.notification.domain.model.entity.Notification;
import com.betalyze.notification.domain.model.query.GetUserFcmTokenQuery;
import com.betalyze.notification.domain.model.query.GetUserNotificationsQuery;
import com.betalyze.notification.domain.model.query.GetUserUnreadNotificationsQuery;
import com.betalyze.notification.domain.service.NotificationQueryService;
import com.betalyze.notification.infrastructure.persistance.jpa.repository.NotificationRepository;
import com.betalyze.notification.infrastructure.persistance.jpa.repository.UserNotificastionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {

  private final NotificationRepository notificationRepository;
  private final UserNotificastionRepository userNotificationRepository;

  @Transactional(readOnly = true)
  @Override
  public List<Notification> handle(GetUserNotificationsQuery query) {
    List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(query.getUserId());

    return notifications;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Notification> handle(GetUserUnreadNotificationsQuery query) {
    List<Notification> notifications = notificationRepository
        .findByUserIdAndReadFalseOrderByCreatedAtDesc(query.getUserId());

    return notifications;
  }

  @Transactional(readOnly = true)
  @Override
  public String handle(GetUserFcmTokenQuery query) {
    UserNotification user = userNotificationRepository.findByUserId(query.getUserId())
        .orElseThrow(() -> new RuntimeException("User FCM token not found"));

    return user.getFcmToken();
  }
}
