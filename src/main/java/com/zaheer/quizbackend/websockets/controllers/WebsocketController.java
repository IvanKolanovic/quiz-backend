package com.zaheer.quizbackend.websockets.controllers;

import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


//TODO: napravit kad dogovorimo pravila
@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

  @MessageMapping("/connect")
  @SendTo("/topic/greetings")
  public WebsocketPayload greeting(WebsocketPayload payload) {
    return null;
  }
}
