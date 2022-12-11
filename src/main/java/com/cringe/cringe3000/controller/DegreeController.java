package com.cringe.cringe3000.controller;

import com.cringe.cringe3000.model.dto.DegreeDTO;
import com.cringe.cringe3000.service.DegreeService;
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
public class DegreeController {

    private final DegreeService service;

    @GetMapping("/degrees")
    public List<DegreeDTO> findAll() {
        return service.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/degree/{id}  ")
    public boolean create(@PathVariable Long id, @Valid @RequestBody DegreeDTO degreeDTO) {
        return service.create(id, degreeDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/degree/{id}")
    public boolean update(@PathVariable Long id, @Valid @RequestBody DegreeDTO degreeDTO) {
        return service.update(id, degreeDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/degree/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
