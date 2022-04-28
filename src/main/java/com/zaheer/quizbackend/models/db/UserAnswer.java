package com.zaheer.quizbackend.models.db;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_answer")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  private Question question;

  @ManyToOne(optional = false)
  @JoinColumn(name = "choice_id", referencedColumnName = "id")
  private QuestionChoices choice;

  @ManyToOne(optional = false)
  @JoinColumn(name = "game_id", referencedColumnName = "id")
  private Game game;

  @Column(name = "is_right")
  private Boolean isRight;

  @Column(name = "answer_time")
  private LocalDateTime answerTime;
}
