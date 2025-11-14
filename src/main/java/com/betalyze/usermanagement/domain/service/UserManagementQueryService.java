package com.betalyze.usermanagement.domain.service;

import com.betalyze.usermanagement.domain.model.agreggate.User;
import com.betalyze.usermanagement.domain.model.query.GetUserProfileQuery;

public interface UserManagementQueryService {
  User handle(GetUserProfileQuery query);
}
