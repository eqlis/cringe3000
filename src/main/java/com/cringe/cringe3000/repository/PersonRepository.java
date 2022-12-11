package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

  @Query("select true from Person p where p.id = :id and (p.user.email = :username or p.user.username = :username)")
  boolean existsByIdAndPrincipal(@Param("id") Long id, @Param("username") String username);

  @Query("with selected as(select * from Person where person.first_name=:firstName and person.last_name=:lastName and person.surname=:surname\n" +
          "and person.experience=:experience and person.degree_id=:degreeId)\n" +
          "select selected.* from selected natural join person_subject where person_subject.person_id=:subjectId\n")
  Page<Person> getPersonsByParams(Pageable page, @Param("firstName") String firstName,
                                  @Param("lastName") String lastName, @Param("surname") String surname,
                                  @Param("experience") int experience, @Param("degreeId") Long degreeId,
                                  @Param("subjectId") Long subjectId);

}
