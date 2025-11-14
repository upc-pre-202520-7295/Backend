package com.betalyze.notification.domain.model.query;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserUnreadNotificationsQuery {
  private UUID userId;
}
