package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.dto.FilterParams;
import com.cringe.cringe3000.model.dto.PageResponse;
import com.cringe.cringe3000.model.dto.PersonBase;
import com.cringe.cringe3000.model.dto.PersonRequest;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import com.cringe.cringe3000.model.dto.PersonResponse;
import com.cringe.cringe3000.model.entity.Degree;
import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.entity.Photo;
import com.cringe.cringe3000.model.entity.Subject;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.enums.Role;
import com.cringe.cringe3000.repository.DegreeRepository;
import com.cringe.cringe3000.repository.PersonRepository;
import com.cringe.cringe3000.repository.PhotoRepository;
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
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepository repository;
  private final DegreeRepository degreeRepository;
  private final SubjectRepository subjectRepository;
  private final PhotoRepository photoRepository;
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
  public boolean create(Long id, PersonRequest personRequest, UserDetails userDetails) {
    if (repository.existsById(id)) {
      log.error("Person with id = " + id + " already exists");
      return false;
    }
    save(id, personRequest, userDetails);
    return true;
  }

  @Override
  @Transactional
  public boolean update(Long id, PersonRequest personRequest, UserDetails userDetails) {
    User user = (User) userDetails;
    if (repository.existsByIdAndPrincipal(id, user.getUsername()) || (repository.existsById(id) && user.getRole() == Role.ADMIN)) {
      save(id, personRequest, userDetails);
      return true;
    }
    log.error("No person with id = " + id + " or it does not belong to user with username = " + userDetails.getUsername());
    return false;
  }

  @Override
  @Transactional
  public boolean delete(Long id, UserDetails userDetails) {
    User user = (User) userDetails;
    if (repository.existsByIdAndPrincipal(id, user.getUsername()) || (repository.existsById(id) && user.getRole() == Role.ADMIN)) {
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

  @Override
  @Transactional
  public void addPhoto(UserDetails userDetails, MultipartFile file, Long id) {
    User user = (User) userDetails;
    if (repository.existsByIdAndPrincipal(id, user.getUsername()) || (repository.existsById(id) && user.getRole() == Role.ADMIN)) {
      Person person = repository.findById(id).orElseThrow(EntityNotFoundException::new);
      try {
        Photo photo = Photo.from(person)
          .photo(file.getBytes())
          .person(person)
          .build();
        person.addPhoto(photo);
        person.setSelectedPhoto(photo.getIndex());
        repository.save(person);
      } catch (IOException ex) {
        log.error("Unable to get photo bytes", ex);
      }
    } else {
      log.error("No person with id = " + id + " or it does not belong to user with username = " + userDetails.getUsername());
      throw new EntityNotFoundException();
    }
  }

  @Override
  public byte[] retrievePhoto(Long id, Integer index) {
    Person person = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    Photo photo = person.getPhotos().stream().filter(p -> Objects.equals(p.getIndex(), index)).findFirst().orElseThrow(EntityNotFoundException::new);
    return photo.getPhoto();
  }

  @Override
  @Transactional
  public void removePhoto(Long id, Integer index) {
    Person person = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    person.removePhoto(index);
    repository.save(person);
  }

  private void save(Long id, PersonRequest personRequest, UserDetails userDetails) {
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
    person.setPhotos(photoRepository.findAllByPersonId(person.getId()));
    person.setId(id);
    person.setUser(user);
    repository.save(person);
  }

}
