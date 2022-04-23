package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Game;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameService {
  Game create(Game game);

  Game get(Long id);

  List<Game> getAll();

  @Transactional
  Game joinGame(Long id, Game input);

  boolean isNameInUse(String name);
}
