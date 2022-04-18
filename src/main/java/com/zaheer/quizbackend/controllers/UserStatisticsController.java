package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.UserStatistics;
import com.zaheer.quizbackend.services.interfaces.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class UserStatisticsController {

  private final UserStatisticsService userStatisticsService;

  @GetMapping("/stat/{id}")
  public ResponseEntity<Object> getStatistic(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userStatisticsService.getStatistic(id));
  }

  @GetMapping("/stat/user/{id}")
  public ResponseEntity<Object> getStatisticByUserId(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userStatisticsService.getStatisticByUserId(id));
  }

  @GetMapping("/stats")
  public ResponseEntity<Object> getAllStatistics() {
    return ResponseEntity.ok(userStatisticsService.getAllStatistics());
  }

  @PutMapping("/stat/user/{id}")
  public ResponseEntity<Object> updateStat(
      @PathVariable(value = "id") Long id, @RequestBody UserStatistics userStatistics) {
    return ResponseEntity.ok(userStatisticsService.updateStatistic(id, userStatistics));
  }
}
