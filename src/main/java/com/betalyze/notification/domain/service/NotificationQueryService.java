package com.betalyze.notification.domain.service;

import java.util.List;

import com.betalyze.notification.domain.model.entity.Notification;
import com.betalyze.notification.domain.model.query.GetUserFcmTokenQuery;
import com.betalyze.notification.domain.model.query.GetUserNotificationsQuery;
import com.betalyze.notification.domain.model.query.GetUserUnreadNotificationsQuery;

public interface NotificationQueryService {
  List<Notification> handle(GetUserNotificationsQuery query);

  List<Notification> handle(GetUserUnreadNotificationsQuery query);

  String handle(GetUserFcmTokenQuery query);
}
