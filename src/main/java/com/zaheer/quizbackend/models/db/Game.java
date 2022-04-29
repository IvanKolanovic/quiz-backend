package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "game")
public class Game {

  @Transient List<User> users;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(nullable = false)
  private Integer players;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "locked")
  private Boolean locked;

  @JsonIgnore
  @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
  private List<Question> questions = List.of();

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<UserAnswer> userAnswers = List.of();

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<Participants> participants = List.of();

  public List<User> getUsers() {
    return participants.stream().map(Participants::getUser).collect(Collectors.toList());
  }
}
