package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class GameLogic {

  private final GameService gameService;

  public void joinGame(WebsocketPayload<Game> payload) {}
}
