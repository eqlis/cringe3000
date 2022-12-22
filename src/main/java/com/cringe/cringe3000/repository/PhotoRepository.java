package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

  List<Photo> findAllByPersonId(Long personId);

}
