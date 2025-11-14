package com.betalyze.usermanagement.application.internal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalyze.shared.infrastructure.exception.NotFoundException;
import com.betalyze.usermanagement.domain.model.agreggate.User;
import com.betalyze.usermanagement.domain.model.query.GetUserProfileQuery;
import com.betalyze.usermanagement.domain.service.UserManagementQueryService;
import com.betalyze.usermanagement.infrastructure.persistance.jpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementQueryServiceImpl implements UserManagementQueryService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public User handle(GetUserProfileQuery query) {
    User user = userRepository.findById(query.getUserId())
        .orElseThrow(() -> new NotFoundException("User not found"));

    return user;
  }
}
