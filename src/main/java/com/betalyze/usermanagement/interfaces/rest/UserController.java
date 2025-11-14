package com.betalyze.usermanagement.interfaces.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.shared.application.dto.Response;
import com.betalyze.usermanagement.domain.model.agreggate.User;
import com.betalyze.usermanagement.domain.model.query.GetUserProfileQuery;
import com.betalyze.usermanagement.domain.service.UserManagementQueryService;
import com.betalyze.usermanagement.interfaces.resources.UserProfileResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users", description = "User management endpoints")
@Slf4j
public class UserController {

  private final UserManagementQueryService userQueryService;

  @GetMapping("/user/{userId}/profile")
  @Operation(summary = "Get user profile")
  public ResponseEntity<Response<UserProfileResource>> getProfile(@PathVariable UUID userId) {
    log.info("[UserController.getProfile] userId: {}", userId);

    User user = userQueryService.handle(new GetUserProfileQuery(userId));

    log.info("[UserController.getProfile] User.getFullName(): {}", user.getFullName());

    UserProfileResource resource = new UserProfileResource(
        user.getEmail(),
        user.getFullName());

    log.info("[UserController.getProfile] resource: {}", resource);

    return ResponseEntity.ok(Response.success(resource));
  }
}
