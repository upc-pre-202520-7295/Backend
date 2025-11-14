package com.betalyze.usermanagement.infrastructure.hashing.bcrypt.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashingServiceImpl implements BCriptHashingService {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public String encode(CharSequence password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
  }
}
