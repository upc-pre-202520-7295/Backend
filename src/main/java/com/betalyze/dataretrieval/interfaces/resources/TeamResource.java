package com.betalyze.dataretrieval.interfaces.resources;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamResource {
  private UUID id;
  private String name;
  private String imgUrl;
}
