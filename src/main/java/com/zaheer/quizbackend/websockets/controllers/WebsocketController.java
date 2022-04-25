package com.zaheer.quizbackend.websockets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.db.UserAnswer;
import com.zaheer.quizbackend.websockets.Greeting;
import com.zaheer.quizbackend.websockets.HelloMessage;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.GameQuestion;
import com.zaheer.quizbackend.websockets.service.interfaces.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ObjectMapper objectMapper;
  private final WebSocketService webSocketService;

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) {
    return new Greeting("Hello! " + HtmlUtils.htmlEscape(message.getName()));
  }

  @MessageMapping("/user-connected")
  public void connected(@Payload User user) {
    simpMessagingTemplate.convertAndSendToUser(
        user.getUsername(), "/queue", webSocketService.connected(user));
  }

  @MessageMapping("/join-game")
  public void joinGame(@Payload WebsocketPayload<Game> payload) {
    log.info("=== Payload: {}", payload);
    WebsocketPayload<Game> newPayload = webSocketService.joinGame(payload);

    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
    log.info("=== Join Game: {}", newPayload);
  }

  @MessageMapping("/start-game")
  public void startGame(@Payload WebsocketPayload<Game> payload) {
    WebsocketPayload<List<Participants>> newPayload = webSocketService.startGame(payload);
    newPayload
        .getContent()
        .forEach(
            participants ->
                simpMessagingTemplate.convertAndSendToUser(
                    participants.getUser().getUsername(), "/queue", newPayload));
    log.info("=== Game Started: {}", newPayload);
  }

  @MessageMapping("/leave-game")
  public void leaveLiveGame(@Payload WebsocketPayload<Participants> payload) {
    WebsocketPayload<Participants> newPayload =
        webSocketService.leaveLiveGame(payload.getContent());
    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
    log.info("=== Left Game: {}", newPayload);
  }

  @MessageMapping("/send-questions")
  public void sendQuestions(@Payload WebsocketPayload<Game> payload) {
    WebsocketPayload<List<GameQuestion>> newPayload =
        webSocketService.prepareQuestions(payload.getContent());
    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
    log.info("=== Send Questions: {}", newPayload);
  }

  @MessageMapping("/evaluate-answer")
  public void evaluateUserAnswer(@Payload WebsocketPayload<UserAnswer> payload) {
    WebsocketPayload<EvaluatedAnswer> newPayload =
        webSocketService.evaluateAnswer(payload.getContent());
    newPayload
        .getUsers()
        .forEach(
            user ->
                simpMessagingTemplate.convertAndSendToUser(
                    user.getUsername(), "/queue", newPayload));
    log.info("=== Evaluate Answer: {}", newPayload);
  }

  public String convert(Object o) {
    try {
      return objectMapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      log.error("Error mapping Object to JSON!");
      throw new RequestFailedException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error mapping Object to JSON!");
    }
  }
}
