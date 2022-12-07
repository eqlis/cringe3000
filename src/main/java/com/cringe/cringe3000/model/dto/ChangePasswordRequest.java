package com.cringe.cringe3000.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.cringe.cringe3000.util.Constants.PASSWORD_VALIDATION;
import static com.cringe.cringe3000.util.Constants.PATTERN;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {

  private final String oldPassword;

  @NotEmpty(message = REQUIRED)
  @Pattern(regexp = PASSWORD_VALIDATION, message = PATTERN)
  private final String newPassword;
}
