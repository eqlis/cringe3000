package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<Jwt, String> {
}
