package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.security.annotations.isAdmin;
import com.zaheer.quizbackend.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @isAdmin
  @PutMapping("/user/{id}")
  public ResponseEntity<Object> updateUser(
      @PathVariable(value = "id") Long id, @RequestBody User user) {
    return ResponseEntity.ok(userService.updateUser(id, user));
  }

  @isAdmin
  @PutMapping("/user/ban/{id}")
  public ResponseEntity<Object> banUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.banUser(id));
  }

  @isAdmin
  @PutMapping("/user/{id}/updateIndex")
  public ResponseEntity<Object> updateLearningIndex(@PathVariable(value = "id") Long id, @RequestParam int learningIndex) {
    return ResponseEntity.ok(userService.updateUserLearningIndex(id, learningIndex));
  }

  @isAdmin
  @DeleteMapping("/user/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.deleteUser(id));
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  @GetMapping("/users")
  public ResponseEntity<Object> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
