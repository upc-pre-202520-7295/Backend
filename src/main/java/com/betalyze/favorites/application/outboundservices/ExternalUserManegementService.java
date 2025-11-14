package com.betalyze.favorites.application.outboundservices;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.betalyze.favorites.interfaces.resources.ExistUserResource;
import com.betalyze.usermanagement.interfaces.acl.UserContextFacade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalUserManegementService {
  private final UserContextFacade userContextFacade;

  public boolean existUser(UUID userId) {
    return userContextFacade.existUser(userId);
  }

  public Optional<ExistUserResource> getUserById(UUID userId) {
    try {
      var userProfile = userContextFacade.getUserProfile(userId);
      return Optional.of(new ExistUserResource(userId, userProfile.getEmail()));
    } catch (Exception e) {
      log.error("Error getting user profile", e);
      return Optional.empty();
    }
  }
}
