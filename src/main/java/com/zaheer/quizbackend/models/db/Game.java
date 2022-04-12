package com.zaheer.quizbackend.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
  @JoinColumn(name = "player_one", referencedColumnName = "id", nullable = false)
  private User playerOne;

  @ManyToOne(optional = false)
  @JoinColumn(name = "player_two", referencedColumnName = "id", nullable = false)
  private User playerTwo;

  @Column(name = "player_one_score", nullable = false)
  private Integer playerOneScore;

  @Column(name = "player_two_score", nullable = false)
  private Integer playerTwoScore;

  @ManyToOne(optional = false)
  @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
  private Room room;
}
