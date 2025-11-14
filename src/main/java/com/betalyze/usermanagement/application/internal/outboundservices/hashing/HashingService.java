package com.betalyze.usermanagement.application.internal.outboundservices.hashing;

public interface HashingService {
  String encode(CharSequence password);

  boolean matches(CharSequence rawPassword, String encodedPassword);
}
