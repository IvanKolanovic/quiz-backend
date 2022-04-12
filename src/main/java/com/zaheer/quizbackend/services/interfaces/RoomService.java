package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Room;

import java.util.List;

public interface RoomService {
  Room createRoom(Room room);

  Room getRoom(Long id);

  List<Room> getAllRooms();

  Room updateRoom(Long id, Room input);

  void deleteRoom(Long id);

  boolean isNameInUse(String name);
}
