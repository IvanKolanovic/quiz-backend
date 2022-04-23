package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "game")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(nullable = false)
  private int players;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "active")
  private Boolean active;

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<Question> questions = List.of();

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<UserAnswer> userAnswers = List.of();

  @JsonIgnore
  @OneToMany(mappedBy = "game")
  private List<Participants> participants = List.of();
}
