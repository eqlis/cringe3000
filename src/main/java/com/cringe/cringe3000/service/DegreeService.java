package com.cringe.cringe3000.service;

import com.cringe.cringe3000.model.dto.DegreeDTO;

import java.util.List;

public interface DegreeService {

    List<DegreeDTO> findAll();

    boolean create(Long id, DegreeDTO degreeDTO);

    boolean update(Long id, DegreeDTO degreeDTO);

    boolean delete(Long id);

}
