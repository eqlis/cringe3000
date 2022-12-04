package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("select u from User u where u.email = :username or u.username = :username")
  Optional<User> findByEmailOrUsername(@Param("username") String username);

}
