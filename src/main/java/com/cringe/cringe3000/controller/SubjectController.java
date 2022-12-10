package com.cringe.cringe3000.controller;

import com.cringe.cringe3000.model.dto.SubjectDTO;
import com.cringe.cringe3000.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class SubjectController {

  private final SubjectService service;

  @GetMapping("/subjects")
  public List<SubjectDTO> findAll() {
    return service.findAll();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/subject/{id}")
  public boolean create(@PathVariable Long id, @Valid @RequestBody SubjectDTO subjectDTO) {
    return service.create(id, subjectDTO);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/subject/{id}")
  public boolean update(@PathVariable Long id, @Valid @RequestBody SubjectDTO subjectDTO) {
    return service.update(id, subjectDTO);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/subject/{id}")
  public boolean delete(@PathVariable Long id) {
    return service.delete(id);
  }

}
