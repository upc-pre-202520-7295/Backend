package com.betalyze.usermanagement.interfaces.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;
}
