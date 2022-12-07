package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.annotation.EmailExists;
import com.cringe.cringe3000.annotation.UsernameExists;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.cringe.cringe3000.util.Constants.EMAIL_VALIDATION;
import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.PASSWORD_VALIDATION;
import static com.cringe.cringe3000.util.Constants.PATTERN;
import static com.cringe.cringe3000.util.Constants.REQUIRED;
import static com.cringe.cringe3000.util.Constants.USERNAME_VALIDATION;

@Getter
@AllArgsConstructor
public class RegisterRequest {

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  @EmailExists
  @Pattern(regexp = EMAIL_VALIDATION, message = PATTERN)
  private final String email;

  @JsonProperty("login")
  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  @UsernameExists
  @Pattern(regexp = USERNAME_VALIDATION, message = PATTERN)
  private final String username;

  @NotEmpty(message = REQUIRED)
  @Pattern(regexp = PASSWORD_VALIDATION, message = PATTERN)
  private final String password;

  @NotEmpty(message = REQUIRED)
  private final Role role;

  public User toUser() {
    return User.builder()
        .email(email)
        .username(username)
        .password(password)
        .role(role)
        .build();
  }

}
