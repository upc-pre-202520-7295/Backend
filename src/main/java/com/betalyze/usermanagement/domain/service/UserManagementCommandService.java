package com.betalyze.usermanagement.domain.service;

import org.springframework.data.util.Pair;

import com.betalyze.usermanagement.domain.model.command.LoginCommand;
import com.betalyze.usermanagement.domain.model.command.RegisterUserCommand;
import com.betalyze.usermanagement.domain.model.agreggate.User;

public interface UserManagementCommandService {

  Pair<User, String> handle(RegisterUserCommand command);

  Pair<User, String> handle(LoginCommand command);
}
