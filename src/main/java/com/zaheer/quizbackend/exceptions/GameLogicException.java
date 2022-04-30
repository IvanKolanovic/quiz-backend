package com.zaheer.quizbackend.exceptions;

import com.zaheer.quizbackend.models.enums.SocketRequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GameLogicException {
  private SocketRequestType type;
}
