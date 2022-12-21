package com.cringe.cringe3000.repository;

import com.cringe.cringe3000.model.dto.FilterParams;
import com.cringe.cringe3000.model.dto.PersonBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonCustomRepository {

  Page<PersonBase> findByParams(FilterParams params, Pageable pageable);

}
