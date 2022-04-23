package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String roles;

  @Column(name = "active")
  private Boolean active;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_statistics_id")
  private UserStatistics userStatistics;

  @Column(nullable = false, name = "learning_index")
  private int learningIndex;

  @Transient private String fullName;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<UserAnswer> userAnswers = List.of();

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Participants> participants = List.of();

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }
}
