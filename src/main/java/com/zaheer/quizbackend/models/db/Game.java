package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @ManyToOne(optional = false)
  @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
  private Room room;

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
