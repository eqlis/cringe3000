package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.model.dto.DegreeDTO;
import com.cringe.cringe3000.model.entity.Degree;
import com.cringe.cringe3000.repository.DegreeRepository;
import com.cringe.cringe3000.service.DegreeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DegreeServiceImpl implements DegreeService {

    private final DegreeRepository repository;

    public List<DegreeDTO> findAll() {
        return repository.findAll().stream().map(DegreeDTO::from).toList();
    }

    public boolean create(Long id, DegreeDTO degreeDTO) {
        if (repository.existsById(id)) {
            log.error("Degree with id = " + id + " already exists");
            return false;
        }
        repository.save(degreeDTO.toDegree());
        return true;
    }

    public boolean update(Long id, DegreeDTO degreeDTO) {
        if (repository.existsById(id)) {
            Degree degree = degreeDTO.toDegree();
            degree.setId(id);
            repository.save(degree);
            return true;
        }
        log.error("No degree with id = " + id);
        return false;
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        log.error("No degree with id = " + id);
        return false;
    }
}
