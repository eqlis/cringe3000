package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.ChangePasswordRequest;
import com.cringe.cringe3000.model.dto.LoginRequest;
import com.cringe.cringe3000.model.dto.RegisterRequest;

public interface UserService {

  String authenticate(LoginRequest loginRequest);

  boolean register(RegisterRequest registerRequest);

  void activate(String token);

  boolean forgotPassword(String email);

  void changePassword(ChangePasswordRequest changePasswordRequest, String token);

}