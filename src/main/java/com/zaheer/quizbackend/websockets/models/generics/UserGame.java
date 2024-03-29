package com.zaheer.quizbackend.websockets.models.generics;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserGame {

  private Game game;
  private User user;
}
