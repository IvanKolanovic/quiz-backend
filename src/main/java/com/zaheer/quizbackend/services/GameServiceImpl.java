package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.repos.CountryRepository;
import com.zaheer.quizbackend.repos.GameRepository;
import com.zaheer.quizbackend.repos.QuestionRepository;
import com.zaheer.quizbackend.services.interfaces.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl extends BaseService implements GameService {

  private final GameRepository gameRepository;
  private final CountryRepository countryRepository;
  private final QuestionRepository questionRepository;

  @Override
  public Game create(Game game) {
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
}
