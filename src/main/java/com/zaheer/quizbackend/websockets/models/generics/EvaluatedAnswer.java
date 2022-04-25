package com.zaheer.quizbackend.websockets.models.generics;

import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.models.db.UserAnswer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EvaluatedAnswer {

  private Participants user;
  private UserAnswer userAnswer;
}
