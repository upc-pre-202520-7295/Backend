package com.betalyze.usermanagement.domain.model.agreggate;

import java.util.UUID;

import com.betalyze.shared.domain.Entity;
import com.betalyze.usermanagement.domain.model.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends Entity {

  @Column(name = "id", updatable = false, nullable = false)
  @Id
  private UUID id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String fullName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role = UserRole.USER;

  public User(String email, String password, String fullName) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
  }
}
