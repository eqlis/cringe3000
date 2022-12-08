package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.cringe.cringe3000.util.Constants.EMAIL_VALIDATION;
import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.PATTERN;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class PersonDTO {

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

  @NotEmpty(message = REQUIRED)
  private final int age;

  @NotEmpty(message = REQUIRED)
  private final int experience;

  @Size(max = 50, message = MAX)
  private final String phone;

  @Size(max = 1000, message = MAX)
  private final String bio;

  private final Gender gender;

  public static PersonDTO from(Person p) {
    return new PersonDTO(
        p.getId(),
        p.getFirstName(),
        p.getLastName(),
        p.getSurname(),
        p.getEmail(),
        p.getAge(),
        p.getExperience(),
        p.getPhone(),
        p.getBio(),
        p.getGender());
  }

  public Person toPerson() {
    Person p = new Person();
    p.setId(id);
    p.setFirstName(firstName);
    p.setLastName(lastName);
    p.setSurname(surname);
    p.setEmail(email);
    p.setAge(age);
    p.setExperience(experience);
    p.setPhone(phone);
    p.setBio(bio);
    p.setGender(gender);
    return p;
  }

}
