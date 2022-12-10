package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

  @Query("select true from Person p where p.id = :id and (p.user.email = :username or p.user.username = :username)")
  boolean existsByIdAndPrincipal(@Param("id") Long id, @Param("username") String username);

}
