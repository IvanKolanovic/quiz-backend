package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.*;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameService {
  Game create(Game game);

  Game get(Long id);

  List<Game> getAll();

  @Transactional
  Object joinGame(UserGame payload);

  @Transactional
  WebsocketPayload<List<Participant>> startGame(Game game, List<Participant> participants);

  @Transactional
  void checkAndUpdateGameStatus(Game input);

  @Transactional
  Participant leaveLiveGame(Participant participant, Game game);

  @Transactional
  void leaveGameRoom(Participant participant, Game game);

  @Transactional
  void prepareQuestions(Game payload);

  @Transactional
  List<Question> createQuestions(Game game);

  @Transactional
  List<QuestionChoices> createChoices(Question question);

  @Transactional
  EvaluatedAnswer evaluateUserAnswer(UserAnswer userAnswer);

  @Transactional
  void applyScore(Participant participant);

  boolean isNameInUse(String name);

    @Transactional
    List<Participant> updateWinner(List<Participant> participants);
}
