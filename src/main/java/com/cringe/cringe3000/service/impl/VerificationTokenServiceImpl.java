package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.entity.VerificationToken;
import com.cringe.cringe3000.repository.VerificationTokenRepository;
import com.cringe.cringe3000.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

  private final long tokenValidityInSeconds;

  private final VerificationTokenRepository verificationTokenRepository;

  public VerificationTokenServiceImpl(@Value("${verification.token.validity}") Duration tokenValidityInSeconds, VerificationTokenRepository verificationTokenRepository) {
    this.tokenValidityInSeconds = tokenValidityInSeconds.toSeconds();
    this.verificationTokenRepository = verificationTokenRepository;
  }

  @Override
  public VerificationToken createVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setExpireAt(LocalDateTime.now().plusSeconds(tokenValidityInSeconds));
    verificationToken.setUser(user);
    verificationTokenRepository.save(verificationToken);
    return verificationToken;
  }

  @Override
  public Optional<VerificationToken> findByToken(String token) {
    return verificationTokenRepository.findByToken(token);
  }

  @Override
  public void verifyToken(VerificationToken verificationToken) {
    verificationToken.setVerified(true);
    verificationTokenRepository.save(verificationToken);
  }

}
