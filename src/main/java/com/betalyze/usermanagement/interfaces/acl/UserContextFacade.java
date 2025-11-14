package com.betalyze.usermanagement.interfaces.acl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.betalyze.usermanagement.domain.model.agreggate.User;
import com.betalyze.usermanagement.domain.model.query.GetUserProfileQuery;
import com.betalyze.usermanagement.domain.service.UserManagementQueryService;
import com.betalyze.usermanagement.interfaces.resources.UserProfileResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserContextFacade {

  private final UserManagementQueryService userManagementQueryService;

  public UserProfileResource getUserProfile(UUID userId) throws Exception {
    User user = userManagementQueryService.handle(new GetUserProfileQuery(userId));

    return new UserProfileResource(
        user.getEmail(),
        user.getFullName());
  }

  public boolean existUser(UUID userId) {
    log.info("[UserContextFacade.existUser] Checking if user {} exists", userId);
    try {
      userManagementQueryService.handle(new GetUserProfileQuery(userId));
      log.info("[UserContextFacade.existUser] User {} exists", userId);
      return true;
    } catch (Exception e) {
      log.info("[UserContextFacade.existUser] User {} does not exist", userId);
      log.error("Error checking user existence", e);
      return false;
    }
  }
}
