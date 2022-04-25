package com.zaheer.quizbackend.websockets.service.interfaces;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.db.UserAnswer;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.GameQuestion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WebSocketService {

  @Transactional
  WebsocketPayload<String> connected(User user);

  @Transactional
  WebsocketPayload<Game> joinGame(WebsocketPayload<Game> payload);

  @Transactional
  WebsocketPayload<List<Participants>> startGame(WebsocketPayload<Game> payload);

  @Transactional
  WebsocketPayload<List<GameQuestion>> prepareQuestions(Game game);

  @Transactional
  WebsocketPayload<EvaluatedAnswer> evaluateAnswer(UserAnswer userAnswer);

  @Transactional
  WebsocketPayload<Participants> leaveLiveGame(Participants payload);
}
