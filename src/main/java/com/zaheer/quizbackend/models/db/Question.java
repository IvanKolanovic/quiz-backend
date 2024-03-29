package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "question")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String abbr;

  @ManyToOne(optional = false)
  @JoinColumn(name = "game_id", referencedColumnName = "id")
  private Game game;

  @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
  private List<QuestionChoices> questionChoices = List.of();

  @JsonIgnore
  @OneToMany(mappedBy = "question")
  private List<UserAnswer> userAnswers = List.of();
}
