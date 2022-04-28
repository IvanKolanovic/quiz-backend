package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "country")
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String continent;

  @Column(nullable = false)
  private String capital;

  @Column(nullable = false)
  private String description;

  @Column(name = "name_abbr", nullable = false)
  private String nameAbbr;

  @JsonIgnore
  @OneToMany(mappedBy = "choice")
  private List<QuestionChoices> questionChoices = List.of();
}
