package com.cringe.cringe3000.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_token")
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String token;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdOn;

  @Basic(optional = false)
  @Column(updatable = false)
  private LocalDateTime expireAt;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  private boolean verified;

  public boolean isNotExpired() {
    return LocalDateTime.now().isBefore(expireAt);
  }

}
