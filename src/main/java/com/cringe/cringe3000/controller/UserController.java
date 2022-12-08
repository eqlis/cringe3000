package com.cringe.cringe3000.controller;

import com.cringe.cringe3000.model.dto.ChangePasswordRequest;
import com.cringe.cringe3000.model.dto.LoginRequest;
import com.cringe.cringe3000.model.dto.RegisterRequest;
import com.cringe.cringe3000.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/auth")
  public String authenticate(@Valid @RequestBody LoginRequest loginRequest) {
    return userService.authenticate(loginRequest);
  }

  @PostMapping("/register")
  public Boolean register(@Valid @RequestBody RegisterRequest registerRequest) {
    return userService.register(registerRequest);
  }

  @GetMapping("/activate/{token}")
  @ResponseStatus(HttpStatus.OK)
  public void activate(@PathVariable("token") String token) {
    userService.activate(token);
  }

  @PostMapping("/forgot")
  public Boolean forgotPassword(@RequestParam("email") String email) {
    return userService.forgotPassword(email);
  }

  @PutMapping("/change-password/{token}")
  @ResponseStatus(HttpStatus.OK)
  public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable("token") String token) {
    userService.changePassword(changePasswordRequest, token);
  }

  @PostMapping("/reset/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void resetPassword(@PathVariable("id") Long id) {
    userService.resetPassword(id);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logout() {
    userService.logout();
  }

}
