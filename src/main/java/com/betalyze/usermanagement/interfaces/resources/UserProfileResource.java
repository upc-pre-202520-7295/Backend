package com.betalyze.usermanagement.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResource {
  private String email;
  private String fullName;
}
