package com.zaheer.quizbackend.websockets.controllers;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.db.UserAnswer;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.GameQuestion;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
import com.zaheer.quizbackend.websockets.service.interfaces.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final WebSocketService webSocketService;

  @MessageMapping("/user-connected")
  public void connected(@Payload User user) {
    simpMessagingTemplate.convertAndSendToUser(
        user.getUsername(), "/queue", webSocketService.connected(user));
  }

  @MessageMapping("/join-game")
  public void joinGame(@Payload UserGame joinGame) {
    WebsocketPayload<Game> newPayload = webSocketService.joinGame(joinGame);

    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
  }

  @MessageMapping("/start-game")
  public void startGame(@Payload Game game) {
    log.info("Start game");
    WebsocketPayload<List<Participants>> newPayload = webSocketService.startGame(game);
    newPayload
        .getContent()
        .forEach(
            participants ->
                simpMessagingTemplate.convertAndSendToUser(
                    participants.getUser().getUsername(), "/queue", newPayload));
    sendQuestions(game);
  }

  @MessageMapping("/leave-live-game")
  public void leaveLiveGame(@Payload UserGame payload) {
    WebsocketPayload<Participants> newPayload = webSocketService.leaveLiveGame(payload);
    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
  }

  @MessageMapping("/send-questions")
  public void sendQuestions(@Payload Game payload) {
    WebsocketPayload<List<GameQuestion>> newPayload = webSocketService.prepareQuestions(payload);
    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
  }

  @MessageMapping("/evaluate-answer")
  public void evaluateUserAnswer(@Payload UserAnswer payload) {
    WebsocketPayload<EvaluatedAnswer> newPayload = webSocketService.evaluateAnswer(payload);
    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
  }

  @MessageMapping("/finished-game")
  public void finishedGame(@Payload UserGame payload) {
    WebsocketPayload<List<Participants>> newPayload = webSocketService.finishedGame(payload);
    if (newPayload == null) return;

    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
  }
}
