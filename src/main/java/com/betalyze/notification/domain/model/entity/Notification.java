package com.betalyze.notification.domain.model.entity;

import java.time.LocalDateTime;

import com.betalyze.notification.domain.model.agreggate.UserNotification;
import com.betalyze.shared.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification extends Entity {

  @ManyToOne
  @JoinColumn(name = "user_notification_id", nullable = false)
  private UserNotification userNotification;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 1000)
  private String message;

  @Column(nullable = false)
  private Boolean sent = false;

  @Column(nullable = false)
  private String data;

  private LocalDateTime sentAt;

  @Column(nullable = false)
  private Boolean read = false;

  @Column(nullable = false)
  private String imageUrl;

  private LocalDateTime readAt;

  private LocalDateTime createdAt;

  private String firebaseMessageId;

  public Notification(String title, String message, String imageUrl, String data) {
    this.title = title;
    this.message = message;
    this.data = data;
    this.imageUrl = imageUrl;
    this.createdAt = LocalDateTime.now();
  }

  public void markAsSent(String firebaseMessageId) {
    this.sent = true;
    this.sentAt = LocalDateTime.now();
    this.firebaseMessageId = firebaseMessageId;
  }

  public void markAsRead() {
    this.read = true;
    this.readAt = LocalDateTime.now();
  }
}
