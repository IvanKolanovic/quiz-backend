package com.zaheer.quizbackend.websockets.models.generics;

import com.zaheer.quizbackend.models.db.Participant;
import com.zaheer.quizbackend.models.db.UserAnswer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EvaluatedAnswer {

  private Participant user;
  private UserAnswer userAnswer;
}
