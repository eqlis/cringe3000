package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>, PersonCustomRepository {
}
