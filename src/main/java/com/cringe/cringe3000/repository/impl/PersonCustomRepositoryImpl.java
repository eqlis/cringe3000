package com.cringe.cringe3000.repository.impl;

import com.cringe.cringe3000.model.dto.FilterParams;
import com.cringe.cringe3000.model.dto.PersonBase;
import com.cringe.cringe3000.model.dto.PhotoInfo;
import com.cringe.cringe3000.model.enums.Gender;
import com.cringe.cringe3000.repository.PersonCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class PersonCustomRepositoryImpl implements PersonCustomRepository {

  private final JdbcTemplate jdbcTemplate;

  public Page<PersonBase> findByParams(FilterParams params, Pageable pageable) {
    String selectWhereGroupBy = "select p.* from person p join person_subject ps on p.id = ps.person_id " +
      "join degree d on p.degree_id = d.id join subject s on ps.subject_id = s.id";

    String orderByLimitOffset = "";

    if (params != null) {
      if (params.getName() != null || params.getExperience() != null || params.getDegreeId() != null || params.getSubjectId() != null) {
        selectWhereGroupBy += " where ";
      }
      if (params.getName() != null) {
        String name = '%' + params.getName().toLowerCase().replace(' ', '%') + '%';
        selectWhereGroupBy += "(lower(surname) || lower(first_name) || lower(last_name) like '" + name + "' or " +
          "lower(surname) || lower(last_name) || lower(first_name) like '" + name + "' or " +
          "lower(first_name) || lower(surname) || lower(last_name) like '" + name + "' or " +
          "lower(first_name) || lower(last_name) || lower(surname) like '" + name + "' or " +
          "lower(last_name) || lower(first_name) || lower(surname) like '" + name + "' or " +
          "lower(last_name) || lower(surname) || lower(first_name) like '" + name + "') and ";
      }
      if (params.getExperience() != null) {
        selectWhereGroupBy += "experience = " + params.getExperience() + " and ";
      }
      if (params.getDegreeId() != null) {
        selectWhereGroupBy += "degree_id = " + params.getDegreeId() + " and ";
      }
      if (params.getSubjectId() != null) {
        selectWhereGroupBy += params.getSubjectId() + " in (select subject_id from person join person_subject on id = person_id where id = p.id)";
      }
      if (selectWhereGroupBy.endsWith("and ")) {
        selectWhereGroupBy = selectWhereGroupBy.substring(0, selectWhereGroupBy.length() - 4);
      }
      selectWhereGroupBy += " group by p.id";

      if (params.getSortParam() != null) {
        orderByLimitOffset += " order by ";
        if (params.isAscending()) {
          switch (params.getSortParam()) {
            case NAME -> orderByLimitOffset += "surname, first_name, last_name";
            case EXPERIENCE -> orderByLimitOffset += "experience";
            case DEGREE -> orderByLimitOffset += "d.name";
            case SUBJECT -> orderByLimitOffset += "s.name";
          }
        } else {
          switch (params.getSortParam()) {
            case NAME -> orderByLimitOffset += "surname desc, first_name desc, last_name desc";
            case EXPERIENCE -> orderByLimitOffset += "experience";
            case DEGREE -> orderByLimitOffset += "d.name";
            case SUBJECT -> orderByLimitOffset += "s.name";
          }
        }
      }
    } else {
      selectWhereGroupBy += " group by p.id";
    }
    orderByLimitOffset += " limit " + pageable.getPageSize();
    orderByLimitOffset += " offset " + pageable.getPageNumber() * pageable.getPageSize();

    List<PersonBase> results = jdbcTemplate.query(selectWhereGroupBy + orderByLimitOffset, (rs, i) ->
      new PersonBase(
        rs.getLong("id"),
        new PhotoInfo(rs.getInt("selected_photo") == 0 ? null : rs.getInt("selected_photo"),
          jdbcTemplate.queryForObject("select count(*) from photo where person_id = " + rs.getLong("id"), Integer.class)),
        rs.getString("last_name"),
        rs.getString("first_name"),
        rs.getString("surname"),
        Objects.equals(rs.getString("gender"), Gender.FEMALE.getDisplayName()) ? Gender.FEMALE : Gender.MALE,
        LocalDate.parse(rs.getString("birthday")),
        rs.getString("phone")
      )
    );

    Long totalElements = jdbcTemplate.queryForObject("select count(res) from (" + selectWhereGroupBy + ") as res", Long.class);
    return new PageImpl<>(results, pageable, totalElements);
  }

}
