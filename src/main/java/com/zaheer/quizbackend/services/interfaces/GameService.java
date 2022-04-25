package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.*;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.GameQuestion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameService {
  Game create(Game game);

  Game get(Long id);

  List<Game> getAll();

  @Transactional
  Game joinGame(WebsocketPayload<Game> payload);

  @Transactional
  Game startGame(Game game);

  @Transactional
  void checkAndUpdateGameStatus(Game input);

  @Transactional
  Participants leaveLiveGameRoom(Participants participant);

  @Transactional
  List<GameQuestion> prepareQuestions(Game payload);

  @Transactional
  List<Question> createQuestions(Game game);

  @Transactional
  List<QuestionChoices> createChoices(Question question);

  @Transactional
  EvaluatedAnswer evaluateUserAnswer(UserAnswer userAnswer);

  boolean isNameInUse(String name);
}
