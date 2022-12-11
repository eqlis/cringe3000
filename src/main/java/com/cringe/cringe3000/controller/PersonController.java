package com.cringe.cringe3000.controller;

import com.cringe.cringe3000.model.dto.*;
import com.cringe.cringe3000.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PostMapping("/person/{id}")
  public boolean create(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDTO) {
    return service.create(id, personDTO, userDetails);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PutMapping("/person/{id}")
  public boolean update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDTO) {
    return service.update(id, personDTO, userDetails);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @DeleteMapping("/person/{id}")
  public boolean delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id) {
    return service.delete(id, userDetails);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public PageResponse sortByPar(@RequestBody FilterParams filterParams, @PageableDefault(size=8) Pageable pageable) {
    return service.findPersons(filterParams, pageable);
  }

}
