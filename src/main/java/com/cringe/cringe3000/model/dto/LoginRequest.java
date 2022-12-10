package com.cringe.cringe3000.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class LoginRequest {

  @JsonProperty("login")
  @NotEmpty(message = REQUIRED)
  private final String username;

  @NotEmpty(message = REQUIRED)
  private final String password;

}
