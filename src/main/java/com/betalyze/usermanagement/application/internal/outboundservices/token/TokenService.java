package com.betalyze.usermanagement.application.internal.outboundservices.token;

public interface TokenService {
  String generateToken(String username);

  boolean isTokenValid(String token, String username);

  String extractUsername(String token);
}
