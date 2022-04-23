package com.zaheer.quizbackend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl {

 /* private final RoomRepository roomRepository;

  @Override
  public Room createRoom(Room room) {

    if (isNameInUse(room.getName()))
      throw new RequestFailedException(
          HttpStatus.BAD_REQUEST, "Room name " + room.getName() + " is taken.");

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

  @Override
  @Transactional
  public Room updateRoom(Long id, Room input) {
    return roomRepository
        .findById(id)
        .map(
            room -> {
              room.setName(input.getName());
              room.setPassword(input.getPassword());
              room.setPlayers(input.getPlayers());
              return room;
            })
        .orElseThrow(resourceNotFound("Room with id: " + id + " was not found."));
  }

  @Override
  @Transactional
  public Room joinRoom(Long id, Room input) {
    Room r =
        roomRepository
            .findById(id)
            .map(
                room -> {
                  if (input.getName().equals(room.getName())
                      && input.getPassword().equals(room.getPassword())) {
                    room.setPlayers(room.getPlayers() + 1);
                  }
                  return room;
                })
            .orElseThrow(resourceNotFound("Room with id: " + id + " was not found."));

    return roomRepository.saveAndFlush(r);
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
  }*/
}
