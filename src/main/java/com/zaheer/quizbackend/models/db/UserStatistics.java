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
@Table(name = "user_statistics")
@Entity
public class UserStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer totalPoints;

  @Column(nullable = false)
  private Integer gamesWon;

  @ManyToOne(optional = false)
  @JoinColumn(name = "rank_id", referencedColumnName = "id", nullable = false)
  private Rank rank;
}
