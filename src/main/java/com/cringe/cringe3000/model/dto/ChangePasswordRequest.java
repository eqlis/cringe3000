package com.cringe.cringe3000.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {

  private final String oldPassword;
  // TODO: Check if valid
  private final String newPassword;
}
