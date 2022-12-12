package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.entity.Jwt;

public interface JwtService {

  boolean jwtIsNotExpired(String token);

  void refresh(String token);

  boolean existsById(String token);

  void save(Jwt jwt);

}
