package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class SubjectDTO {

  private final Long id;

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private final String name;

  public static SubjectDTO from(Subject s) {
    return new SubjectDTO(s.getId(), s.getName());
  }

  public Subject toSubject() {
    return new Subject(id, name);
  }

}
