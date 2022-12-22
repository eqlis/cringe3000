package com.cringe.cringe3000.controller;

import com.cringe.cringe3000.model.dto.FilterParams;
import com.cringe.cringe3000.model.dto.PageResponse;
import com.cringe.cringe3000.model.dto.PersonLightDTO;
import com.cringe.cringe3000.model.dto.PersonRequest;
import com.cringe.cringe3000.model.dto.PersonResponse;
import com.cringe.cringe3000.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
  public PersonResponse findById(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PostMapping("/person/{id}")
  public boolean create(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, @Valid @RequestBody PersonRequest person) {
    return service.create(id, person, userDetails);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PutMapping("/person/{id}")
  public boolean update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, @Valid @RequestBody PersonRequest person) {
    return service.update(id, person, userDetails);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @DeleteMapping("/person/{id}")
  public boolean delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id) {
    return service.delete(id, userDetails);
  }

  @GetMapping("/page/{pageNumber}")
  public PageResponse sortByPar(@RequestBody(required = false) FilterParams filterParams, @PathVariable("pageNumber") Integer pageNumber) {
    return service.findPersons(filterParams, pageNumber);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PostMapping(value = "/person/{id}/photo", consumes = { "multipart/form-data" })
  public void uploadPhoto(@AuthenticationPrincipal UserDetails userDetails, @RequestParam MultipartFile photo, @PathVariable("id") Long id) {
    service.addPhoto(userDetails, photo, id);
  }

  @GetMapping(value = "/person/{id}/photo/{index}", produces = MediaType.IMAGE_JPEG_VALUE)
  public byte[] retrievePhoto(@PathVariable("id") Long id, @PathVariable("index") Integer index) {
    return service.retrievePhoto(id, index);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @DeleteMapping("/person/{id}/photo/{index}")
  public void removePhoto(@PathVariable("id") Long id, @PathVariable("index") Integer index) {
    service.removePhoto(id, index);
  }

}
