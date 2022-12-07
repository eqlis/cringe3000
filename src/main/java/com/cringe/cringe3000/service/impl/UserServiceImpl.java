package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.auth.JwtUtils;
import com.cringe.cringe3000.model.dto.ChangePasswordRequest;
import com.cringe.cringe3000.model.dto.LoginRequest;
import com.cringe.cringe3000.model.dto.RegisterRequest;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.entity.VerificationToken;
import com.cringe.cringe3000.repository.UserRepository;
import com.cringe.cringe3000.service.EmailService;
import com.cringe.cringe3000.service.UserService;
import com.cringe.cringe3000.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.cringe.cringe3000.util.Constants.CHANGE_PASSWORD;
import static com.cringe.cringe3000.util.Constants.CONFIRMATION_INSTRUCTION;
import static com.cringe.cringe3000.util.Constants.CONFIRMATION_LINK;
import static com.cringe.cringe3000.util.Constants.FORGOT_PASSWORD_INSTRUCTION;
import static com.cringe.cringe3000.util.Constants.REGISTRATION_CONFIRMATION;
import static com.cringe.cringe3000.util.Constants.CHANGE_PASSWORD_LINK;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder encoder;
  private final VerificationTokenService verificationTokenService;
  private final EmailService emailService;

  @Override
  public String authenticate(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtUtils.generateJwtToken(authentication);
  }

  @Override
  @Transactional
  public boolean register(RegisterRequest registerRequest) {
    User user = registerRequest.toUser();
    user.setPassword(encoder.encode(user.getPassword()));
    userRepository.save(user);
    VerificationToken verificationToken = verificationTokenService.createVerificationToken(user);
    String text = CONFIRMATION_INSTRUCTION + " " + CONFIRMATION_LINK + verificationToken.getToken();
    emailService.sendMail(user.getEmail(), REGISTRATION_CONFIRMATION, text);
    return true;
  }

  @Override
  @Transactional
  public void activate(String token) {
    Optional<VerificationToken> tokenOptional = verificationTokenService.findByToken(token)
        .filter(vt -> vt.isNotExpired() && !vt.isVerified());
    if (tokenOptional.isEmpty()) {
      throw new EntityNotFoundException();
    }
    VerificationToken verificationToken = tokenOptional.get();
    User user = verificationToken.getUser();
    user.setEnabled(true);
    userRepository.save(user);
    verificationTokenService.verifyToken(verificationToken);
  }

  @Override
  @Transactional
  public boolean forgotPassword(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      VerificationToken verificationToken = verificationTokenService.createVerificationToken(user.get());
      String text = FORGOT_PASSWORD_INSTRUCTION + " " + CHANGE_PASSWORD_LINK + verificationToken.getToken();
      emailService.sendMail(email, CHANGE_PASSWORD, text);
    }
    return true;
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordRequest changePasswordRequest, String token) {
    Optional<VerificationToken> tokenOptional = verificationTokenService.findByToken(token)
        .filter(vt -> vt.isNotExpired() && !vt.isVerified());
    if (tokenOptional.isEmpty()) {
      throw new EntityNotFoundException();
    }
    VerificationToken verificationToken = tokenOptional.get();
    User user = verificationToken.getUser();
    if (encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
      user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
      userRepository.save(user);
      verificationTokenService.verifyToken(verificationToken);
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

}
