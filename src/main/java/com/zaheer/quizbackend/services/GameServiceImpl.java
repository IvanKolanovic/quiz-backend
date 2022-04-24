package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.repos.GameRepository;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
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
  private final ParticipantsServiceImpl participantsUserService;

  @Override
  public Game create(Game game) {
    if (isNameInUse(game.getName()))
      throw new RequestFailedException(
          HttpStatus.BAD_REQUEST, "Game name: " + game.getName() + " is taken.");

    game.setLocked(Optional.ofNullable(game.getPassword()).isPresent());
    game.setActive(true);
    game.setStarted(false);
    game.setPlayers(1);
    return gameRepository.saveAndFlush(game);
  }

  @Override
  public Game get(Long id) {
    return gameRepository
        .findByIdAndActiveTrue(id)
        .orElseThrow(resourceNotFound("Game with id: " + id + " was not found."));
  }

  @Override
  public List<Game> getAll() {
    return gameRepository.findAllByActiveTrue();
  }

  @Override
  @Transactional
  public Game joinGame(WebsocketPayload<Game> payload) {

    Game input = payload.getContent();
    Game g =
        gameRepository
            .findByIdAndActiveTrue(input.getId())
            .map(
                game -> {
                  if (game.getPlayers() == 2)
                    throw new RequestFailedException(HttpStatus.CONFLICT, "Game is full.");
                  if (input.getName().equals(game.getName())) {
                    if (Optional.ofNullable(input.getPassword()).isPresent()) {
                      if (input.getPassword().equals(game.getPassword()))
                        game.setPlayers(game.getPlayers() + 1);
                      else throw new RequestFailedException(HttpStatus.CONFLICT, "Wrong password.");
                    } else game.setPlayers(game.getPlayers() + 1);
                  } else {
                    throw new RequestFailedException(HttpStatus.CONFLICT, "Wrong game name.");
                  }
                  return game;
                })
            .orElseThrow(resourceNotFound("Game with id: " + input.getId() + " was not found."));

    Participants p = Participants.builder().user(payload.getUser()).game(g).build();
    participantsUserService.create(p);

    return gameRepository.saveAndFlush(g);
  }

  @Override
  @Transactional
  public void checkAndDeleteGame(Long id, Game input) {
    Game g =
        gameRepository
            .findByIdAndActiveTrue(id)
            .orElseThrow(resourceNotFound("Game with id: " + id + " was not found."));

    if (input.getPlayers() < 2 && input.getStarted()) {
      g.setActive(false);
      gameRepository.saveAndFlush(g);
    } else if (input.getPlayers() == 0 && !input.getStarted()) {
      gameRepository.delete(g);
    }
  }

  @Override
  public boolean isNameInUse(String name) {
    Optional<Game> game = gameRepository.findByNameAndActiveTrue(name);
    return game.isPresent();
  }
}
