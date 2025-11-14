package com.betalyze.usermanagement.interfaces.rest;

import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.shared.application.dto.Response;
import com.betalyze.usermanagement.domain.model.agreggate.User;
import com.betalyze.usermanagement.domain.model.command.LoginCommand;
import com.betalyze.usermanagement.domain.model.command.RegisterUserCommand;
import com.betalyze.usermanagement.domain.service.UserManagementCommandService;
import com.betalyze.usermanagement.interfaces.resources.AuthResponseResource;
import com.betalyze.usermanagement.interfaces.resources.LoginUserRequest;
import com.betalyze.usermanagement.interfaces.resources.RegisterUserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication endpoints")
@Slf4j
public class AuthController {

  private final UserManagementCommandService userCommandService;

  @PostMapping("/register")
  @Operation(summary = "Register new user")
  public ResponseEntity<Response<AuthResponseResource>> register(@Valid @RequestBody RegisterUserRequest request) {

    log.info("[AuthController.register] request: {}", request);

    Pair<User, String> response = userCommandService.handle(new RegisterUserCommand(
        request.getEmail(),
        request.getPassword(),
        request.getFullName()));

    log.info("[AuthController.register] response: {}", response);

    AuthResponseResource resource = new AuthResponseResource(
        response.getFirst().getId().toString(),
        response.getFirst().getEmail(),
        response.getFirst().getFullName(),
        response.getSecond());

    log.info("[AuthController.register] resource: {}", resource);

    return ResponseEntity.ok(Response.success("User registered successfully", resource));
  }

  @PostMapping("/login")
  @Operation(summary = "Login user")
  public ResponseEntity<Response<AuthResponseResource>> login(@Valid @RequestBody LoginUserRequest request) {

    log.info("[AuthController.login] request: {}", request);

    Pair<User, String> response = userCommandService.handle(new LoginCommand(
        request.getEmail(),
        request.getPassword()));

    log.info("[AuthController.login] response: {}", response);

    AuthResponseResource resource = new AuthResponseResource(
        response.getFirst().getId().toString(),
        response.getFirst().getEmail(),
        response.getFirst().getFullName(),
        response.getSecond());

    log.info("[AuthController.login] resource: {}", resource);

    return ResponseEntity.ok(Response.success("Login successful", resource));
  }
}
