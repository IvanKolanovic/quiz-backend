package com.zaheer.quizbackend.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question_choices")
@Entity
@Builder
@ToString
public class QuestionChoices {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  private Question question;

  @ManyToOne(optional = false)
  @JoinColumn(name = "choice", referencedColumnName = "id")
  private Country choice;

  @Column(name = "is_right")
  private Boolean isRight;

  @JsonIgnore
  @OneToMany(mappedBy = "choice")
  private List<UserAnswer> userAnswers = List.of();
}
