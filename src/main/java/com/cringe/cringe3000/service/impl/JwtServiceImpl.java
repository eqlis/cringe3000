package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.entity.Jwt;
import com.cringe.cringe3000.repository.JwtRepository;
import com.cringe.cringe3000.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final JwtRepository jwtRepository;

  @Override
  public boolean jwtIsNotExpired(String token) {
    return jwtRepository.findById(token).filter(jwt -> jwt.getExpireAt().isAfter(Instant.now()) && jwt.isActive()).isPresent();
  }

  @Override
  public void refresh(String token) {
    Jwt jwt = jwtRepository.findById(token).orElseThrow(EntityNotFoundException::new);
    jwt.setExpireAt(Instant.now().plus(1, ChronoUnit.HOURS));
    jwtRepository.save(jwt);
  }

  @Override
  public boolean existsById(String token) {
    return jwtRepository.existsById(token);
  }

  @Override
  public void save(Jwt jwt) {
    jwtRepository.save(jwt);
  }

}
