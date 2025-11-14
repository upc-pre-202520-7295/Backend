package com.betalyze.notification.domain.model.agreggate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.betalyze.notification.domain.model.entity.Notification;
import com.betalyze.shared.domain.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "user_notifications")
@Getter
@Setter
@NoArgsConstructor
public class UserNotification extends Entity {
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(nullable = false, unique = true)
  private String fcmToken;

  @OneToMany(mappedBy = "userNotification", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Notification> notifications = new ArrayList<>();

  public UserNotification(UUID userId, String fcmToken) {
    this.userId = userId;
    this.fcmToken = fcmToken;
  }
}
