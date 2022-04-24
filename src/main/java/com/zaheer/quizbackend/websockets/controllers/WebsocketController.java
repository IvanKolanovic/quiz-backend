package com.zaheer.quizbackend.websockets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.websockets.Greeting;
import com.zaheer.quizbackend.websockets.HelloMessage;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.service.interfaces.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ObjectMapper objectMapper;
  private final WebSocketService webSocketService;

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    log.info("Radi jer si " + message.getName());
    return new Greeting("Hello! " + HtmlUtils.htmlEscape(message.getName()));
  }

  @MessageMapping("/user-connected")
  @SendToUser("/queue/connected")
  public Object connected(User user) {
    return convert(webSocketService.connected(user));
  }

  public Object convert(Object o) {
    try {
      return objectMapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      log.error("Error mapping Object to JSON!");
      throw new RequestFailedException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error mapping Object to JSON!");
    }
  }
}
