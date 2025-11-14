package com.betalyze.usermanagement.application.internal.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.shared.infrastructure.exception.DomainException;
import com.betalyze.usermanagement.application.internal.outboundservices.hashing.HashingService;
import com.betalyze.usermanagement.application.internal.outboundservices.token.TokenService;
import com.betalyze.usermanagement.domain.model.agreggate.User;
import com.betalyze.usermanagement.domain.model.command.LoginCommand;
import com.betalyze.usermanagement.domain.model.command.RegisterUserCommand;
import com.betalyze.usermanagement.domain.service.UserManagementCommandService;
import com.betalyze.usermanagement.infrastructure.persistance.jpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementCommandServiceImpl implements UserManagementCommandService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final HashingService hashingService;

  @Transactional
  public Pair<User, String> handle(RegisterUserCommand command) {
    if (userRepository.existsByEmail(command.getEmail())) {
      throw new DomainException("Email already registered");
    }

    String password = hashingService.encode(command.getPassword());

    User user = new User(
        command.getEmail(),
        password,
        command.getFullName());

    user = userRepository.save(user);

    String token = tokenService.generateToken(user.getEmail());

    return Pair.of(user, token);
  }

  @Transactional(readOnly = true)
  public Pair<User, String> handle(LoginCommand command) {
    User user = userRepository.findByEmail(command.getEmail()).orElseThrow(() -> new DomainException("User not found"));

    if (!hashingService.matches(command.getPassword(), user.getPassword())) {
      throw new DomainException("Invalid password");
    }

    String token = tokenService.generateToken(user.getEmail());

    return Pair.of(user, token);
  }
}
