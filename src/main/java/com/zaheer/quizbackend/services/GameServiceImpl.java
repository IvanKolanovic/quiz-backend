package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.repos.GameRepository;
import com.zaheer.quizbackend.services.interfaces.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl extends BaseService implements GameService {

  private final GameRepository gameRepository;

  @Override
  public Game create(Game game) {
    if (isNameInUse(game.getName()))
      throw new RequestFailedException(
          HttpStatus.BAD_REQUEST, "Game name: " + game.getName() + " is taken.");

    game.setStarted(false);
    game.setPlayers(1);
    return gameRepository.saveAndFlush(game);
  }

  @Override
  public Game get(Long id) {
    return gameRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Game with id: " + id + " was not found."));
  }

  @Override
  public List<Game> getAll() {
    return gameRepository.findAll();
  }

  @Override
  @Transactional
  public Game joinGame(Long id, Game input) {
    Game g =
        gameRepository
            .findById(id)
            .map(
                game -> {
                  if (input.getName().equals(game.getName())) {
                    if (input.getPassword().equals(game.getPassword()))
                      game.setPlayers(game.getPlayers() + 1);
                  }
                  return game;
                })
            .orElseThrow(resourceNotFound("Game with id: " + id + " was not found."));

    return gameRepository.saveAndFlush(g);
  }

  @Override
  public boolean isNameInUse(String name) {
    Optional<Game> game = gameRepository.findByName(name);
    return game.isPresent();
  }
}
