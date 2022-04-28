package com.zaheer.quizbackend.models.db;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "participants")
@ToString
public class Participants {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "game_id", referencedColumnName = "id")
  private Game game;

  @Column(name = "user_score", nullable = false)
  private Integer userScore;

  @Column(name = "in_game")
  private Boolean inGame;

  public void updateScore(int score) {
    this.userScore = this.userScore + score;
  }
}
