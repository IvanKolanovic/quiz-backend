package com.zaheer.quizbackend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocketRequestType {
  Connected,
  Join_Game,
  Left_Game,
  Game_Questions,
  Evaluate_Answer,
  Finished_Game,
  Start_Game;

}
