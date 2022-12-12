package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.ChangePasswordRequest;
import com.cringe.cringe3000.model.dto.LoginRequest;
import com.cringe.cringe3000.model.dto.RegisterRequest;
import com.cringe.cringe3000.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

  String authenticate(LoginRequest loginRequest);

  boolean register(RegisterRequest registerRequest);

  void activate(String token);

  boolean forgotPassword(String email);

  void changePassword(ChangePasswordRequest changePasswordRequest, String token);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  Optional<User> findByEmailOrUsername(String username);

  void resetPassword(Long id, UserDetails userDetails);

  void logout(UserDetails userDetails);

}