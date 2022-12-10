package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

  VerificationToken createVerificationToken(User user);

  Optional<VerificationToken> findByToken(String token);

  void verifyToken(VerificationToken verificationToken);

}
