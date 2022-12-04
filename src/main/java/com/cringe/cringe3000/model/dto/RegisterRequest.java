package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.annotation.EmailExists;
import com.cringe.cringe3000.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.cringe.cringe3000.util.Constants.EMAIL_VALIDATION;
import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.PATTERN;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class RegisterRequest {

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  @EmailExists
  @Pattern(regexp = EMAIL_VALIDATION, message = PATTERN)
  private final String email;

  // TODO: validate if not present
  @JsonProperty("login")
  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private final String username;

  // TODO: check if valid
  @NotEmpty(message = REQUIRED)
  private final String password;

  public User toUser() {
    return User.builder()
        .email(email)
        .username(username)
        .password(password)
        .build();
  }

  // TODO: set role somehow
}
