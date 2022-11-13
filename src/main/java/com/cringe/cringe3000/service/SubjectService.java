package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

  List<SubjectDTO> findAll();

  boolean create(Long id, SubjectDTO subjectDTO);

  boolean update(Long id, SubjectDTO subjectDTO);

  boolean delete(Long id);

}
