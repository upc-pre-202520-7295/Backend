package com.betalyze.usermanagement.infrastructure.persistance.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betalyze.usermanagement.domain.model.agreggate.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findById(UUID id);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
