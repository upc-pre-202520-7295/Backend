package com.betalyze.notification.interfaces.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.notification.domain.model.command.EnsureUserNotificationExists;
import com.betalyze.notification.domain.model.command.MarkAsReadCommand;
import com.betalyze.notification.domain.model.command.SendTestNotification;
import com.betalyze.notification.domain.model.command.UpdateFcmTokenCommand;
import com.betalyze.notification.domain.model.entity.Notification;
import com.betalyze.notification.domain.model.query.GetUserNotificationsQuery;
import com.betalyze.notification.domain.model.query.GetUserUnreadNotificationsQuery;
import com.betalyze.notification.domain.service.NotificationCommandService;
import com.betalyze.notification.domain.service.NotificationQueryService;
import com.betalyze.notification.infrastructure.resources.NotificationResource;
import com.betalyze.notification.infrastructure.resources.SendNotificationBody;
import com.betalyze.shared.application.dto.Response;
import com.betalyze.usermanagement.interfaces.acl.UserContextFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Notifications", description = "User notification endpoints")
public class NotificationController {

  private final NotificationCommandService notificationCommandService;
  private final NotificationQueryService notificationQueryService;
  private final UserContextFacade userContextFacade;

  @GetMapping("/user/{userId}/notifications")
  @Operation(summary = "Get all user notifications")
  public ResponseEntity<Response<List<NotificationResource>>> getUserNotifications(@PathVariable UUID userId) {

    log.info("Getting notifications for user {}", userId);

    List<Notification> notifications = notificationQueryService
        .handle(new GetUserNotificationsQuery(userId));

    log.info("Found {} notifications for user {}", notifications.size(), userId);

    if (notifications.isEmpty()) {
      log.info("No notifications found for user {}", userId);
      return ResponseEntity.notFound().build();
    }

    List<NotificationResource> resources = notifications.stream()
        .map(notification -> new NotificationResource(
            notification.getId(),
            notification.getTitle(),
            notification.getMessage(),
            notification.getSent(),
            notification.getRead(),
            notification.getCreatedAt()))
        .toList();

    log.info("Returning {} notifications for user {}", resources.size(), userId);

    return ResponseEntity.ok(Response.success(resources));
  }

  @GetMapping("/user/{userId}/unread")
  @Operation(summary = "Get unread notifications")
  public ResponseEntity<Response<List<NotificationResource>>> getUnreadNotifications(@PathVariable UUID userId) {
    log.info("Getting unread notifications for user {}", userId);

    List<Notification> notifications = notificationQueryService
        .handle(new GetUserUnreadNotificationsQuery(userId));

    log.info("Found {} unread notifications for user {}", notifications.size(), userId);

    if (notifications.isEmpty()) {
      log.info("No unread notifications found for user {}", userId);
      return ResponseEntity.notFound().build();
    }

    List<NotificationResource> resources = notifications.stream()
        .map(notification -> new NotificationResource(
            notification.getId(),
            notification.getTitle(),
            notification.getMessage(),
            notification.getSent(),
            notification.getRead(),
            notification.getCreatedAt()))
        .toList();

    log.info("Returning {} unread notifications for user {}", resources.size(), userId);

    return ResponseEntity.ok(Response.success(resources));
  }

  @PutMapping("/user/{userId}/notification/{id}/read")
  @Operation(summary = "Mark notification as read")
  public ResponseEntity<Response<Void>> markAsRead(@PathVariable UUID userId, @PathVariable UUID id) {
    log.info("Marking notification {} as read for user {}", id, userId);

    notificationCommandService.handle(new MarkAsReadCommand(userId, id));

    log.info("Notification {} marked as read for user {}", id, userId);

    return ResponseEntity.ok(Response.success("Notification marked as read", null));
  }

  @PutMapping("/user/{userId}/fcm-token/{token}")
  @Operation(summary = "Update user FCM token")
  public ResponseEntity<Response<Void>> updateFcmToken(
      @PathVariable UUID userId,
      @PathVariable String token) {
    log.info("[NotificationController.updateFcmToken] Updating FCM token for user {}", userId);
    try {

      if (!userContextFacade.existUser(userId)) {
        log.warn("User not found for userId {}", userId);
        return ResponseEntity.notFound().build();
      }

      log.info("Trying to create user notification for user {}", userId);
      notificationCommandService.handle(new EnsureUserNotificationExists(userId));

      log.info("Updating FCM token for user {}", userId);
      notificationCommandService.handle(new UpdateFcmTokenCommand(userId, token));

      log.info("FCM token updated for user {}", userId);
      return ResponseEntity.ok(Response.success("FCM token updated", null));
    } catch (Exception e) {
      log.error("[NotificationController.updateFcmToken] Error updating FCM token", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/user/{userId}/notification")
  @Operation(summary = "Send notification to user")
  public ResponseEntity<Response<Void>> sendNotification(
      @PathVariable UUID userId,
      @RequestBody SendNotificationBody request) {
    try {

      log.info("[NotificationController.sendNotification] Sending notification to user {}", userId);

      notificationCommandService.handle(new SendTestNotification(userId, request.getTitle(),
          request.getMessage(), request.getImageUrl()));

      log.info("[NotificationController.sendNotification] Notification sent to user {}", userId);

      return ResponseEntity.ok(Response.success("Notification sent", null));
    } catch (Exception e) {
      log.error("[NotificationController.sendNotification] Error sending notification", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
