package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PersonService {

  List<PersonLightDTO> findAll();

  PersonDTO findById(Long id);

  boolean create(Long id, PersonDTO personDTO, UserDetails userDetails);

  boolean update(Long id, PersonDTO personDTO, UserDetails userDetails);

  boolean delete(Long id, UserDetails userDetails);
  PageResponse findPersons(FilterParams filterParameters, Pageable pageable);

}
