package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.dto.SubjectDTO;
import com.cringe.cringe3000.model.entity.Subject;
import com.cringe.cringe3000.repository.SubjectRepository;
import com.cringe.cringe3000.service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private final SubjectRepository repository;

  @Override
  public List<SubjectDTO> findAll() {
    return repository.findAll().stream().map(SubjectDTO::from).toList();
  }

  @Override
  @Transactional
  public boolean create(Long id, SubjectDTO subjectDTO) {
    if (repository.existsById(id)) {
      log.error("Subject with id = " + id + " already exists");
      return false;
    }
    repository.save(subjectDTO.toSubject());
    return true;
  }

  @Override
  @Transactional
  public boolean update(Long id, SubjectDTO subjectDTO) {
    if (repository.existsById(id)) {
      Subject subject = subjectDTO.toSubject();
      subject.setId(id);
      repository.save(subject);
      return true;
    }
    log.error("No subject with id = " + id);
    return false;
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    log.error("No subject with id = " + id);
    return false;
  }
}
