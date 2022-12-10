package com.cringe.cringe3000.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  USER("USER"),
  ADMIN("ADMIN");

  private final String displayName;
}
