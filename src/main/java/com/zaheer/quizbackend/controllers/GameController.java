package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.repos.UserRepository;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.websockets.controllers.WebSocketController;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;
private final WebSocketController controller;
private final UserRepository userRepository;

  @PostMapping("/game")
  public ResponseEntity<Object> create(@RequestBody Game game) {
    return ResponseEntity.ok(gameService.create(game));
  }

  @GetMapping("/game/{id}")
  public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {

    controller.joinGame(new UserGame(gameService.get(id),userRepository.findUserById(1l).get()));
    return ResponseEntity.ok(gameService.get(id));
  }

  @GetMapping("/games")
  public ResponseEntity<Object> getAll() {
    return ResponseEntity.ok(gameService.getAll());
  }
}
