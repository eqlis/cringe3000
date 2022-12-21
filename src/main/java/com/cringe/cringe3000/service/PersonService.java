package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.FilterParams;
import com.cringe.cringe3000.model.dto.PageResponse;
import com.cringe.cringe3000.model.dto.PersonDTO;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PersonService {

  List<PersonLightDTO> findAll();

  PersonDTO findById(Long id);

  boolean create(Long id, PersonDTO personDTO, UserDetails userDetails);

  boolean update(Long id, PersonDTO personDTO, UserDetails userDetails);

  boolean delete(Long id, UserDetails userDetails);

  PageResponse findPersons(FilterParams filterParameters, Integer pageNumber);

}
