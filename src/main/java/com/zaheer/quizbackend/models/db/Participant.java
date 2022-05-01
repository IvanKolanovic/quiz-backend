package com.zaheer.quizbackend.models.db;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "participants")
@ToString
public class Participant {

  @Column(name = "has_won")
  Boolean hasWon;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "game_id", referencedColumnName = "id")
  private Game game;

  @Column(name = "user_score", nullable = false)
  private Integer userScore;

  @Column(name = "in_game")
  private Boolean inGame;

  @Column(name = "has_left")
  private Boolean hasLeft;

  @Column(name = "finished_at")
  private LocalDateTime finishedAt;

  public void updateScore(int score) {
    this.userScore = this.userScore + score;
  }
}
