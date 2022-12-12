package com.cringe.cringe3000.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwt")
public class Jwt {

  @Id
  private String token;

  private Instant expireAt;

  private boolean active;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
