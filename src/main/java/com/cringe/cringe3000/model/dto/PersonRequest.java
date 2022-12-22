package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

import static com.cringe.cringe3000.util.Constants.DATE_FORMAT;
import static com.cringe.cringe3000.util.Constants.EMAIL_VALIDATION;
import static com.cringe.cringe3000.util.Constants.FUTURE;
import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.NEGATIVE;
import static com.cringe.cringe3000.util.Constants.PATTERN;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class PersonRequest {

  private final Long id;

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private final String firstName;

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private final String lastName;

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private final String surname;

  @Size(max = 256, message = MAX)
  @Pattern(regexp = EMAIL_VALIDATION, message = PATTERN)
  private final String email;

  @JsonFormat(pattern = DATE_FORMAT)
  @NotNull(message = REQUIRED)
  @PastOrPresent(message = FUTURE)
  private final LocalDate birthday;

  @Min(value = 0, message = NEGATIVE)
  private final int experience;

  @Size(max = 50, message = MAX)
  private final String phone;

  @Size(max = 1000, message = MAX)
  private final String bio;

  private final Gender gender;

  private final String[] interests;

  private final String[] publications;

  private final DegreeDTO degree;

  private final List<SubjectDTO> subjects;

  public Person toPerson() {
    Person p = new Person();
    p.setId(id);
    p.setFirstName(firstName);
    p.setLastName(lastName);
    p.setSurname(surname);
    p.setEmail(email);
    p.setBirthday(birthday);
    p.setExperience(experience);
    p.setPhone(phone);
    p.setBio(bio);
    p.setGender(gender);
    p.setInterests(interests);
    p.setPublications(publications);
    p.setDegree(degree == null ? null : degree.toDegree());
    p.setSubjects(subjects == null ? null : subjects.stream().map(SubjectDTO::toSubject).toList());
    return p;
  }

}