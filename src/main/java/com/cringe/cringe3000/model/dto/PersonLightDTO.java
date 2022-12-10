package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonLightDTO {

  private final Long id;
  private final String name;

  public static PersonLightDTO from(Person p) {
    return new PersonLightDTO(p.getId(), p.getFirstName() + " " + p.getLastName());
  }

}
