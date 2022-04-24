package com.zaheer.quizbackend.websockets.controllers;

import com.zaheer.quizbackend.services.GameLogic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

  private final GameLogic gameLogic;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public String greet(String message) throws InterruptedException {
    Thread.sleep(2000);
    return message + " websocket";
  }
}
