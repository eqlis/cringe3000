package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.PersonDTO;
import com.cringe.cringe3000.model.dto.PersonLightDTO;

import java.security.Principal;
import java.util.List;

public interface PersonService {

  List<PersonLightDTO> findAll();

  PersonDTO findById(Long id);

  boolean create(Long id, PersonDTO personDTO, Principal principal);

  boolean update(Long id, PersonDTO personDTO, Principal principal);

  boolean delete(Long id, Principal principal);

}
