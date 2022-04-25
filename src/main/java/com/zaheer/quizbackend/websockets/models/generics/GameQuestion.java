package com.zaheer.quizbackend.websockets.models.generics;

import com.zaheer.quizbackend.models.db.Question;
import com.zaheer.quizbackend.models.db.QuestionChoices;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GameQuestion {

  private Question question;
  private List<QuestionChoices> choices;
}
