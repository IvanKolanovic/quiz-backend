package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Question;
import com.zaheer.quizbackend.repos.QuestionRepository;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.services.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl extends BaseService implements QuestionService {

  private final QuestionRepository questionRepository;
  private final GameService gameService;

  @Override
  @Transactional
  public Question createQuestion(Question question) {
    return questionRepository.saveAndFlush(question);
  }

  @Override
  public Question getQuestion(Long id) {
    return questionRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Question with id: " + id + " was not found."));
  }

  @Override
  public List<Question> getAllQuestionsByGame(Long gameId) {
    Game game = gameService.get(gameId);
    return questionRepository.findAllByGameIdOrderByIdAsc(game.getId());
  }
}
