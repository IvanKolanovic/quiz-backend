package com.zaheer.quizbackend.models.db;

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
@Table(name = "room")
public class Room {

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

  @Column(nullable = false)
  private Boolean start;

  @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
  private List<Game> games;
}
