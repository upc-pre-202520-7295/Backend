package com.betalyze.notification.infrastructure.resources;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendNotificationBody {
  @NotBlank
  private String title;

  @NotBlank
  private String message;

  private String imageUrl;
}
