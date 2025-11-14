package com.betalyze.notification.infrastructure.persistance.jpa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.betalyze.notification.domain.model.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
  @Query("SELECT n FROM Notification n WHERE n.userNotification.userId = :userId")
  List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);

  @Query("SELECT n FROM Notification n WHERE n.userNotification.userId = :userId AND n.read = true")
  List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(UUID userId);

  List<Notification> findBySentFalse();
}
