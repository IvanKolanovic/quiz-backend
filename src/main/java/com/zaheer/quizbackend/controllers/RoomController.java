package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.Room;
import com.zaheer.quizbackend.models.security.annotations.isAdmin;
import com.zaheer.quizbackend.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @PostMapping("/room")
  public ResponseEntity<Object> create(@RequestBody Room room) {
    return ResponseEntity.ok(roomService.createRoom(room));
  }

  @GetMapping("/room/{id}")
  public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(roomService.getRoom(id));
  }

  @GetMapping("/rooms")
  public ResponseEntity<Object> getAll() {
    return ResponseEntity.ok(roomService.getAllRooms());
  }

  @PutMapping("/room/{id}")
  public ResponseEntity<Object> update(
      @PathVariable(value = "id") Long id, @RequestBody Room room) {
    return ResponseEntity.ok(roomService.updateRoom(id, room));
  }

  @isAdmin
  @DeleteMapping("/room/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    roomService.deleteRoom(id);
    return ResponseEntity.ok("True");
  }
}
