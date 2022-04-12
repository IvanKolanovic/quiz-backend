package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Game;

import java.util.List;

public interface GameService {
  Game create(Game game);

  Game get(Long id);

  List<Game> getAll();

  Game updateScore(Long id, Game input);
}
