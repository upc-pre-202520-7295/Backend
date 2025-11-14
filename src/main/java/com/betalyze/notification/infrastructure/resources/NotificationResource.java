package com.betalyze.notification.infrastructure.resources;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResource {
  private UUID id;
  private String title;
  private String message;
  private Boolean sent;
  private Boolean read;
  private LocalDateTime createdAt;
}
