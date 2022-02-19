package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.User;
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
  @PostMapping("/user")
  public ResponseEntity<Object> createUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @isAdmin
  @PutMapping("/user/{id}")
  public ResponseEntity<Object> updateUser(
      @PathVariable(value = "id") Long id, @RequestBody User user) {
    return ResponseEntity.ok(userService.updateUser(id, user));
  }

  @isAdmin
  @DeleteMapping("/user/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.deleteUser(id));
  }

  @isAdmin
  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getUser(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  @isAdmin
  @GetMapping("/users")
  public ResponseEntity<Object> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
