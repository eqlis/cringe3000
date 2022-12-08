package com.cringe.cringe3000.controller;

import com.cringe.cringe3000.model.dto.PersonDTO;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import com.cringe.cringe3000.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class PersonController {

  private final PersonService service;

  @GetMapping("/personnel")
  public List<PersonLightDTO> findAll() {
    return service.findAll();
  }

  @GetMapping("/person/{id}")
  public PersonDTO findById(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @PostMapping("/person/{id}")
  public boolean create(@AuthenticationPrincipal Principal principal, @PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDTO) {
    return service.create(id, personDTO, principal);
  }

  @PutMapping("/person/{id}")
  public boolean update(@AuthenticationPrincipal Principal principal, @PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDTO) {
    return service.update(id, personDTO, principal);
  }

  @DeleteMapping("/person/{id}")
  public boolean delete(@AuthenticationPrincipal Principal principal, @PathVariable("id") Long id) {
    return service.delete(id, principal);
  }

}
