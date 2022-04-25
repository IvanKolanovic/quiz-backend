package com.zaheer.quizbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocketRequestType {
  Connected("Connected"),
  Join_Game("Join_Game"),
  Left_Game("Left_Game"),
  Game_Questions("Game_Questions"),
  Evaluate_Answer("Evaluate_Answer"),
  Start_Game("Start_Game");

  private final String type;
}
