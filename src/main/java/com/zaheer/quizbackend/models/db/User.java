package com.zaheer.quizbackend.models.db;

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
@ToString
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String roles;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_statistics_id")
  private UserStatistics userStatistics;

  @Transient private String fullName;

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }
}
