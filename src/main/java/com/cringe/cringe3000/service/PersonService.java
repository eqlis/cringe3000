package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.FilterParams;
import com.cringe.cringe3000.model.dto.PageResponse;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import com.cringe.cringe3000.model.dto.PersonRequest;
import com.cringe.cringe3000.model.dto.PersonResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {

  List<PersonLightDTO> findAll();

  PersonResponse findById(Long id);

  boolean update(Long id, PersonRequest personRequest, UserDetails userDetails);

  boolean delete(Long id);

  PageResponse findPersons(FilterParams filterParameters, Integer pageNumber);

  void addPhoto(UserDetails userDetails, MultipartFile file, Long id);

  byte[] retrievePhoto(Long id, Integer index);

  void removePhoto(Long id, Integer index);

}
