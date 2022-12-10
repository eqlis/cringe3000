package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
