package com.zaheer.quizbackend.scheduler;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.repos.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GameRoomScheduler {

  private final GameRepository gameRepository;

  @Scheduled(fixedDelay = 15000)
  public void deleteEmptyGames() {
    List<Game> games = gameRepository.findAllByActiveTrue();
    games.forEach(
        game -> {
          if (game.getPlayers().equals(0)) gameRepository.deleteAll(games);
        });
  }
}
