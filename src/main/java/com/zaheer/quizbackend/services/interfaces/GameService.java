package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.*;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.GameQuestion;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameService {
  Game create(Game game);

  Game get(Long id);

  List<Game> getAll();

  @Transactional
  Game joinGame(UserGame payload);

  @Transactional
  WebsocketPayload<List<Participants>> startGame(Game game, List<Participants> participants);

  @Transactional
  void checkAndUpdateGameStatus(Game input);

  @Transactional
  Participants leaveLiveGameRoom(Participants participant);

  @Transactional
  void prepareQuestions(Game payload);

  @Transactional
  List<Question> createQuestions(Game game);

  @Transactional
  List<QuestionChoices> createChoices(Question question);

  @Transactional
  EvaluatedAnswer evaluateUserAnswer(UserAnswer userAnswer);

  boolean isNameInUse(String name);
}
