package com.cringe.cringe3000.model.entity;

import com.cringe.cringe3000.model.enums.Gender;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
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

  private String firstName;

  private String lastName;

  private String surname;

  private String email;

  private LocalDate birthday;

  private int experience;

  private String phone;

  private String bio;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private byte[] photo;

  @Type(type = "string-array")
  @Column(name = "interests", columnDefinition = "varchar(255) []")
  private String[] interests;

  @Type(type = "string-array")
  @Column(name = "publications", columnDefinition = "varchar(255) []")
  private String[] publications;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToMany
  @JoinTable(
    name = "person_subject",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "subject_id")
  )
  private List<Subject> subjects;

  @ManyToOne
  @JoinColumn(name = "degree_id", referencedColumnName = "id")
  private Degree degree;

}
