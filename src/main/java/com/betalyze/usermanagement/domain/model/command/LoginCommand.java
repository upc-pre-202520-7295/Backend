package com.betalyze.usermanagement.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginCommand {

  private String email;
  private String password;
}
