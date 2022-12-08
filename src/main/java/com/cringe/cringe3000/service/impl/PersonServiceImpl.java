package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.dto.PersonDTO;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.repository.PersonRepository;
import com.cringe.cringe3000.service.PersonService;
import com.cringe.cringe3000.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepository repository;
  private final UserService userService;

  @Override
  public List<PersonLightDTO> findAll() {
    return repository.findAll().stream().map(PersonLightDTO::from).toList();
  }

  @Override
  public PersonDTO findById(Long id) {
    return repository.findById(id).map(PersonDTO::from).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public boolean create(Long id, PersonDTO personDTO, Principal principal) {
    User user = userService.findByEmailOrUsername(principal.getName()).orElseThrow(EntityNotFoundException::new);
    if (repository.existsById(id)) {
      log.error("Person with id = " + id + " already exists");
      return false;
    }
    Person person = personDTO.toPerson();
    person.setId(id);
    person.setUser(user);
    repository.save(person);
    return true;
  }

  @Override
  @Transactional
  public boolean update(Long id, PersonDTO personDTO, Principal principal) {
    if (repository.existsByIdAndPrincipal(id, principal.getName())) {
      User user = userService.findByEmailOrUsername(principal.getName()).get();
      Person person = personDTO.toPerson();
      person.setId(id);
      person.setUser(user);
      repository.save(person);
      return true;
    }
    log.error("No person with id = " + id + " or it does not belong to user with username = " + principal.getName());
    return false;
  }

  @Override
  @Transactional
  public boolean delete(Long id, Principal principal) {
    if (repository.existsByIdAndPrincipal(id, principal.getName())) {
      repository.deleteById(id);
      return true;
    }
    log.error("No person with id = " + id + " or it does not belong to user with username = " + principal.getName());
    return false;
  }

}
