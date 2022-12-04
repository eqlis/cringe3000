package com.cringe.cringe3000.model.entity;

import com.cringe.cringe3000.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.cringe.cringe3000.util.Constants.EMAIL_VALIDATION;
import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.PATTERN;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private String firstName;

  @NotEmpty(message = REQUIRED)
  @Size(max = 256, message = MAX)
  private String lastName;

  @Size(max = 256, message = MAX)
  @Pattern(regexp = EMAIL_VALIDATION, message = PATTERN)
  private String email;

  private int age;

  private int experience;

  @Size(max = 50, message = MAX)
  private String phone;

  @Size(max = 1000, message = MAX)
  private String bio;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Gender gender = Gender.MALE;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
