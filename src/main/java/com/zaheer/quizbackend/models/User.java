package com.zaheer.quizbackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Basic
  @Column(unique = true, nullable = false)
  private String email;

  @Basic
  @Column(nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Basic
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Basic
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Basic
  @Column(nullable = false)
  private String roles;

  @Transient private String fullName;

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }
}
