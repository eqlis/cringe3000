package com.cringe.cringe3000.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "photo")
public class Photo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private byte[] photo;

  private Integer index;

  @ManyToOne(optional = false)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person person;

  public static PhotoBuilder from(Person person) {
    return builder().index(nextIndex(person));
  }

  static int nextIndex(Person person) {
    return person.getPhotos().stream().mapToInt(p -> p.getIndex() + 1).max().orElse(1);
  }

}
