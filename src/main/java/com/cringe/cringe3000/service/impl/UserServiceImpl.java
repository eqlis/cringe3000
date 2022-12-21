package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.auth.JwtUtils;
import com.cringe.cringe3000.model.dto.*;
import com.cringe.cringe3000.model.entity.Jwt;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.entity.VerificationToken;
import com.cringe.cringe3000.repository.UserRepository;
import com.cringe.cringe3000.service.EmailService;
import com.cringe.cringe3000.service.JwtService;
import com.cringe.cringe3000.service.UserService;
import com.cringe.cringe3000.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.cringe.cringe3000.util.Constants.CHANGE_PASSWORD;
import static com.cringe.cringe3000.util.Constants.CONFIRMATION_INSTRUCTION;
import static com.cringe.cringe3000.util.Constants.CONFIRMATION_LINK;
import static com.cringe.cringe3000.util.Constants.FORGOT_PASSWORD_INSTRUCTION;
import static com.cringe.cringe3000.util.Constants.PASSWORD_RESET;
import static com.cringe.cringe3000.util.Constants.REGISTRATION_CONFIRMATION;
import static com.cringe.cringe3000.util.Constants.CHANGE_PASSWORD_LINK;
import static org.passay.AllowedCharacterRule.ERROR_CODE;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder encoder;
  private final VerificationTokenService verificationTokenService;
  private final EmailService emailService;
  private final JwtService jwtService;

  @Override
  @Transactional
  public AuthResponse authenticate(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtUtils.generateJwtToken(authentication);
    User user = userRepository.findByEmailOrUsername(loginRequest.getUsername()).orElseThrow(EntityNotFoundException::new);
    UserDTO userDTO = UserDTO.from(user);
    Jwt jwt = new Jwt(token, Instant.now().plus(1, ChronoUnit.HOURS), true, user);
    jwtService.save(jwt);
    return new AuthResponse(userDTO, token);
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
    VerificationToken verificationToken = verificationTokenService.findByToken(token)
        .filter(vt -> vt.isNotExpired() && !vt.isVerified())
        .orElseThrow(EntityNotFoundException::new);
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
    VerificationToken verificationToken = verificationTokenService.findByToken(token)
        .filter(vt -> vt.isNotExpired() && !vt.isVerified())
        .orElseThrow(EntityNotFoundException::new);
    User user = verificationToken.getUser();
    user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
    userRepository.save(user);
    verificationTokenService.verifyToken(verificationToken);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findByEmailOrUsername(String username) {
    return userRepository.findByEmailOrUsername(username);
  }

  @Override
  @Transactional
  public void resetPassword(Long id, UserDetails userDetails) {
    User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    User admin = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new);
    String password = generatePassword();
    user.setPassword(encoder.encode(password));
    userRepository.save(user);
    String text = "Password for " + user.getUsername() + " was changed to: " + password;
    emailService.sendMail(admin.getEmail(), PASSWORD_RESET, text);
  }

  @Override
  public void logout(UserDetails userDetails) {
    User user = userRepository.findByEmailOrUsername(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new);
    Jwt jwt = user.getJwts().stream().filter(t -> t.isActive() && t.getExpireAt().isAfter(Instant.now())).findFirst()
        .orElseThrow(EntityNotFoundException::new);
    jwt.setExpireAt(Instant.now());
    jwt.setActive(false);
    userRepository.save(user);
  }

  private String generatePassword() {
    PasswordGenerator generator = new PasswordGenerator();

    CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
    lowerCaseRule.setNumberOfCharacters(2);
    CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
    upperCaseRule.setNumberOfCharacters(2);
    CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
    digitRule.setNumberOfCharacters(2);

    CharacterData specialChars = new CharacterData() {
      @Override
      public String getErrorCode() {
        return ERROR_CODE;
      }

      @Override
      public String getCharacters() {
        return "!@#$%^&*()_+";
      }
    };
    CharacterRule splCharRule = new CharacterRule(specialChars);
    splCharRule.setNumberOfCharacters(2);

    return generator.generatePassword(12, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
  }

}
