package com.betalyze.favorites.interfaces.resources;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExistUserResource {
  private UUID id;
  private String email;
}
