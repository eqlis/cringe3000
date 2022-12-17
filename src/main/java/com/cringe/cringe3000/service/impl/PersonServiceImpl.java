package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.dto.PersonRequest;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import com.cringe.cringe3000.model.dto.PersonResponse;
import com.cringe.cringe3000.model.entity.Degree;
import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.entity.Subject;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.repository.DegreeRepository;
import com.cringe.cringe3000.repository.PersonRepository;
import com.cringe.cringe3000.repository.SubjectRepository;
import com.cringe.cringe3000.service.PersonService;
import com.cringe.cringe3000.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepository repository;
  private final DegreeRepository degreeRepository;
  private final SubjectRepository subjectRepository;
  private final UserService userService;

  @Override
  public List<PersonLightDTO> findAll() {
    return repository.findAll().stream().map(PersonLightDTO::from).toList();
  }

  @Override
  public PersonResponse findById(Long id) {
    return repository.findById(id).map(PersonResponse::from).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public boolean create(Long id, PersonRequest personRequest, MultipartFile photo, UserDetails userDetails) {
    if (repository.existsById(id)) {
      log.error("Person with id = " + id + " already exists");
      return false;
    }
    save(id, personRequest, photo, userDetails);
    return true;
  }

  @Override
  @Transactional
  public boolean update(Long id, PersonRequest personRequest, MultipartFile photo, UserDetails userDetails) {
    if (repository.existsByIdAndPrincipal(id, userDetails.getUsername())) {
      save(id, personRequest, photo, userDetails);
      return true;
    }
    log.error("No person with id = " + id + " or it does not belong to user with username = " + userDetails.getUsername());
    return false;
  }

  @Override
  @Transactional
  public boolean delete(Long id, UserDetails userDetails) {
    if (repository.existsByIdAndPrincipal(id, userDetails.getUsername())) {
      repository.deleteById(id);
      return true;
    }
    log.error("No person with id = " + id + " or it does not belong to user with username = " + userDetails.getUsername());
    return false;
  }

  @Override
  public PageResponse findPersons(FilterParams filterParameters, Integer pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber - 1, 8);
    Page<PersonBase> page = repository.findByParams(filterParameters, pageable);
    return new PageResponse(page.getContent(), page.getTotalPages());
  }

  private void save(Long id, PersonRequest personRequest, MultipartFile photo, UserDetails userDetails) {
    User user = userService.findByEmailOrUsername(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new);
    Person person = personRequest.toPerson();
    Degree degree = person.getDegree();
    if (degree != null) {
      person.setDegree(degreeRepository.findById(degree.getId()).orElseThrow(EntityNotFoundException::new));
    }
    List<Subject> subjects = person.getSubjects();
    if (subjects != null && !subjects.isEmpty()) {
      person.setSubjects(subjects.stream().map(s -> subjectRepository.findById(s.getId()).orElseThrow(EntityNotFoundException::new)).toList());
    }
    if (!photo.isEmpty()) {
      try {
        person.setPhoto(photo.getBytes());
      } catch (Exception ex) {
        log.error("Unable to get photo bytes", ex);
      }
    }
    person.setId(id);
    person.setUser(user);
    repository.save(person);
  }

}
