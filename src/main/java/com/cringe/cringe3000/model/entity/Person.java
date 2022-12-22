package com.cringe.cringe3000.model.entity;

import com.cringe.cringe3000.model.enums.Gender;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
@Entity
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

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "person_id")
  private List<Photo> photos;

  private Integer selectedPhoto;

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

  public Integer getPhotosSize() {
    return photos == null ? null : photos.size();
  }

  public void addPhoto(Photo photo) {
    if (photos == null) {
      photos = new ArrayList<>();
    }
    photos.add(photo);
  }

  public void removePhoto(Integer index) {
    photos.removeIf(p -> p.getIndex().equals(index));
    photos.sort(Comparator.comparing(Photo::getId));
    for (int i = 0; i < photos.size(); i++) {
      photos.get(i).setIndex(i + 1);
    }
    if (Objects.equals(selectedPhoto, index)) {
      selectedPhoto = getPhotosSize();
    } else if (selectedPhoto > index) {
      selectedPhoto -= 1;
    }
  }

}
