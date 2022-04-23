package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameService {
  Game create(Game game);

  Game get(Long id);

  List<Game> getAll();

  @Transactional
  Game joinGame(WebsocketPayload<Game> payload);

  @Transactional
  void checkAndDeleteGame(Long id, Game input);

  boolean isNameInUse(String name);
}
