package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.AuthResponse;
import com.cringe.cringe3000.model.dto.ChangePasswordRequest;
import com.cringe.cringe3000.model.dto.LoginRequest;
import com.cringe.cringe3000.model.dto.RegisterRequest;
import com.cringe.cringe3000.model.dto.UserDTO;
import com.cringe.cringe3000.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

  AuthResponse authenticate(LoginRequest loginRequest);

  Long register(RegisterRequest registerRequest);

  boolean forgotPassword(String email);

  void changePassword(ChangePasswordRequest changePasswordRequest, String token);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  void resetPassword(Long id, UserDetails userDetails);

  void logout(UserDetails userDetails);

  UserDTO refresh(String token);

}