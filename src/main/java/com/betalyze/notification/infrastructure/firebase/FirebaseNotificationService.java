package com.betalyze.notification.infrastructure.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseNotificationService {

  @Value("${firebase.config.path:}")
  private String firebaseConfigPath;

  @PostConstruct
  public void initialize() {
    try {
      if (firebaseConfigPath != null && !firebaseConfigPath.isEmpty()) {
        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);

        log.info("[FirebaseNotificationService.initialize] Firebase config path: {}", firebaseConfigPath);

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        log.info("[FirebaseNotificationService.initialize] Firebase Options: {}", options);

        if (FirebaseApp.getApps().isEmpty()) {
          FirebaseApp.initializeApp(options);
          log.info("[FirebaseNotificationService.initialize] Firebase initialized successfully");
        }

        log.info("[FirebaseNotificationService.initialize] Firebase was initialized previously");
      } else {
        log.warn(
            "[FirebaseNotificationService.initialize] Firebase config path not set. Notifications will be simulated.");
      }
    } catch (IOException e) {
      log.error("[FirebaseNotificationService.initialize] Error initializing Firebase", e);
    }
  }

  public String sendNotification(String fcmToken, String title, String body, String imageUrl) {
    try {
      if (FirebaseApp.getApps().isEmpty()) {
        log.info("Simulated notification - Token: {}, Title: {}, Body: {}", fcmToken, title, body);
        return "simulated_" + UUID.randomUUID().toString();
      }

      Map<String, String> data = new HashMap<>();
      data.put("fcmToken", fcmToken.toString());

      // Build notification
      Notification notification = Notification.builder()
          .setTitle(title)
          .setBody(body)
          .setImage(imageUrl)
          .build();

      Message message = Message.builder()
          .setNotification(notification)
          .putAllData(data)
          .setToken(fcmToken)
          .build();

      String response = FirebaseMessaging.getInstance().send(message);
      log.info("Successfully sent notification token: {}, Message ID: {}", fcmToken, response);

      return response;

    } catch (Exception e) {
      log.error("Error sending Firebase notification to token {}", fcmToken, e);
      throw new RuntimeException("Failed to send notification", e);
    }
  }
}
