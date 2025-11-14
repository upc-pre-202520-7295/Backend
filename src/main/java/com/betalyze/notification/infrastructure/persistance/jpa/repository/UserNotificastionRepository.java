package com.betalyze.notification.infrastructure.persistance.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betalyze.notification.domain.model.agreggate.UserNotification;

@Repository
public interface UserNotificastionRepository extends JpaRepository<UserNotification, UUID> {
  Optional<UserNotification> findByUserId(UUID userId);
}
