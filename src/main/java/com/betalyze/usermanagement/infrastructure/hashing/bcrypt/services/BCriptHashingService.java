package com.betalyze.usermanagement.infrastructure.hashing.bcrypt.services;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.betalyze.usermanagement.application.internal.outboundservices.hashing.HashingService;

public interface BCriptHashingService extends PasswordEncoder, HashingService {
}
