package com.zaheer.quizbackend.websockets.service;

import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.services.BaseService;
import com.zaheer.quizbackend.services.GameLogic;
import com.zaheer.quizbackend.services.interfaces.UserService;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.service.interfaces.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl extends BaseService implements WebSocketService {

  private final GameLogic gameLogic;
  private final UserService userService;

  @Override
  public WebsocketPayload<String> connected() {
    User user = getCurrentUser();
    log.info("=== User Connected: {}", user);
    return WebsocketPayload.<String>builder()
        .client(user)
        .time(LocalDateTime.now())
        .data("Welcome " + user.getUsername() + " to Flaginator. Enjoy!")
        .build();
  }
}
