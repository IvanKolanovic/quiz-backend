package com.zaheer.quizbackend.websockets.service.interfaces;

import com.zaheer.quizbackend.models.db.*;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WebSocketService {

  @Transactional
  WebsocketPayload<String> connected(User user);

  @Transactional
  WebsocketPayload<Game> joinGame(UserGame game);

  @Transactional
  WebsocketPayload<List<Participant>> startGame(Game payload);

  @Transactional
  WebsocketPayload<List<Question>> prepareQuestions(Game game);

  @Transactional
  WebsocketPayload<EvaluatedAnswer> evaluateAnswer(UserAnswer userAnswer);

  @Transactional(isolation = Isolation.SERIALIZABLE)
  WebsocketPayload<List<Participant>> finishedGame(Long gameId, Long userId);

  @Transactional
  WebsocketPayload<Participant> leave(UserGame payload);
}
