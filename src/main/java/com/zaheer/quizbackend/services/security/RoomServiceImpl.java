package com.zaheer.quizbackend.services.security;

import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.db.Room;
import com.zaheer.quizbackend.repos.RoomRepository;
import com.zaheer.quizbackend.services.BaseService;
import com.zaheer.quizbackend.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl extends BaseService implements RoomService {

  private final RoomRepository roomRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Room createRoom(Room room) {

    if (isNameInUse(room.getName()))
      throw new RequestFailedException(
          HttpStatus.BAD_REQUEST, "Room name " + room.getName() + " is taken.");

    room.setPassword(passwordEncoder.encode(room.getPassword()));
    room.setStart(false);
    room.setPlayers(1);
    return roomRepository.saveAndFlush(room);
  }

  @Override
  public Room getRoom(Long id) {
    return roomRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Room with id: " + id + " was not found."));
  }

  @Override
  public List<Room> getAllRooms() {
    return roomRepository.findAll();
  }

    // TODO: Mili rijesi kako hendlat kad user ude u room itd.
  @Override
  public Room updateRoom(Long id, Room input) {
    return roomRepository
        .findById(id)
        .map(
            room -> {
              room.setName(input.getName());
              room.setPassword(passwordEncoder.encode(input.getPassword()));
              room.setPlayers(input.getPlayers());
              if (room.getPlayers() == 2) room.setStart(true);
              return room;
            })
        .orElseThrow(resourceNotFound("Room with id: " + id + " was not found."));
  }

  @Override
  public void deleteRoom(Long id) {
    Room room = getRoom(id);

    roomRepository.delete(room);
  }

  @Override
  public boolean isNameInUse(String name) {
    Optional<Room> room = roomRepository.findByName(name);
    return room.isPresent();
  }
}
