package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.services.interfaces.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class RankController {

  private final RankService rankService;

  @GetMapping("/rank/{id}")
  public ResponseEntity<Object> getRank(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(rankService.getRankById(id));
  }

  @GetMapping("/ranks")
  public ResponseEntity<Object> getAllRanks() {
    return ResponseEntity.ok(rankService.getAllRanks());
  }
}
