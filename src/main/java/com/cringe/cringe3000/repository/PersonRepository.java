package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

  @Query("select from Person p join p.user u where p.id = :id and u.email = :username or u.username = :username")
  boolean existsByIdAndPrincipal(@Param("id") Long id, @Param("username") String username);

}
