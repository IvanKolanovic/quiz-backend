package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.services.interfaces.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;

  @PostMapping("/game")
  public ResponseEntity<Object> create(@RequestBody Game game) {
    return ResponseEntity.ok(gameService.create(game));
  }

  @GetMapping("/game/{id}")
  public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(gameService.get(id));
  }

  @GetMapping("/games")
  public ResponseEntity<Object> getAll() {
    return ResponseEntity.ok(gameService.getAll());
  }

  @PutMapping("/game/{id}")
  public ResponseEntity<Object> update(
      @PathVariable(value = "id") Long id, @RequestBody Game game) {
    return ResponseEntity.ok(gameService.updateScore(id, game));
  }
}
