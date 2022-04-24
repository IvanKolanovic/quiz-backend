package com.zaheer.quizbackend.websockets.controllers;

import com.zaheer.quizbackend.services.GameLogic;
import com.zaheer.quizbackend.websockets.Greeting;
import com.zaheer.quizbackend.websockets.HelloMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

  private final GameLogic gameLogic;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    log.info("Radi jer si " + message.getName());
    return new Greeting("Hello! " + HtmlUtils.htmlEscape(message.getName()));
  }
}
