package com.betalyze.usermanagement.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserCommand {

  private String email;

  private String password;

  private String fullName;
}
