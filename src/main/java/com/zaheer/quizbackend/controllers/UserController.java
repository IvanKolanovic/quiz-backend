package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.security.annotations.isSuperAdminOrAdmin;
import com.zaheer.quizbackend.repos.UserRepository;
import com.zaheer.quizbackend.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  @PutMapping("/user/{id}")
  public ResponseEntity<Object> updateUser(
      @PathVariable(value = "id") Long id, @RequestBody User user) {
    return ResponseEntity.ok(userService.updateUser(id, user));
  }

  @isSuperAdminOrAdmin
  @PutMapping("/user/admin/{id}")
  public ResponseEntity<Object> adminUpdateUser(
          @PathVariable(value = "id") Long id, @RequestBody User user) {
    return ResponseEntity.ok(userService.adminUpdateUser(id, user));
  }

  @isSuperAdminOrAdmin
  @PutMapping("/user/ban/{id}")
  public ResponseEntity<Object> banUser(@PathVariable(value = "id") Long id, @RequestParam(name = "adminID") Long adminID) {
    return ResponseEntity.ok(userService.banUser(id, adminID));
  }

  @PutMapping("/user/{id}/updateIndex")
  public ResponseEntity<Object> updateLearningIndex(
      @PathVariable(value = "id") Long id, @RequestParam(name = "index") Integer learningIndex) {
    return ResponseEntity.ok(userService.updateUserLearningIndex(id, learningIndex));
  }

  @PutMapping("/user/{id}/setIndex")
  public ResponseEntity<Object> updateSetLearningIndex(
      @PathVariable(value = "id") Long id, @RequestParam(name = "index") Integer learningIndex) {
    return ResponseEntity.ok(userService.updateUserSetLearningIndex(id, learningIndex));
  }

  @isSuperAdminOrAdmin
  @DeleteMapping("/user/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.deleteUser(id));
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  @GetMapping("/user/top/points")
  public ResponseEntity<Object> getTop20ByTotalPoints() {
    Page<User> page = userRepository.findTop20ByTotalPoints(PageRequest.of(0, 20));
    return ResponseEntity.ok(page.getContent());
  }

  @GetMapping("/user/top/won")
  public ResponseEntity<Object> getTop20ByGamesWon() {
    Page<User> page = userRepository.findTop20ByGamesWon(PageRequest.of(0, 20));
    return ResponseEntity.ok(page.getContent());
  }

  @GetMapping("/user/top/games")
  public ResponseEntity<Object> getTop20ByTotalGames() {
    Page<User> page = userRepository.findTop20ByTotalGames(PageRequest.of(0, 20));
    return ResponseEntity.ok(page.getContent());
  }

  @GetMapping("/user/top/average")
  public ResponseEntity<Object> getTop20ByPointAverage() {
    Page<User> page = userRepository.findTop20ByPointAverage(PageRequest.of(0, 20));
    return ResponseEntity.ok(page.getContent());
  }

  @GetMapping("/users")
  public ResponseEntity<Object> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
