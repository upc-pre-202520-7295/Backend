package com.betalyze.shared.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected UUID id;

  @Column(name = "created_at", nullable = false, updatable = false)
  protected LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  protected LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Entity entity = (Entity) o;
    return id != null && Objects.equals(id, entity.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
