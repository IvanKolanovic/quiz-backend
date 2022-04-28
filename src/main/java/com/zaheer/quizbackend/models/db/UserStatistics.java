package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_statistics")
@Entity
@ToString
public class UserStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "total_points", nullable = false)
  private Double totalPoints;

  @Column(nullable = false, name = "games_won")
  private Integer gamesWon;

  @Column(nullable = false, name = "total_game")
  private Integer totalGames;

  @Column(nullable = false, name = "point_average")
  private Double pointAverage;

  @ManyToOne(optional = false)
  @JoinColumn(name = "rank_id", referencedColumnName = "id", nullable = false)
  private Rank rank;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "userStatistics")
  private User user;
}
