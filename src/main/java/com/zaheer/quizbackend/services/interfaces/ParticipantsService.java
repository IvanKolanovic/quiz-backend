package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Participants;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParticipantsService {
  @Transactional
  Participants create(Participants participants);

  List<Participants> getParticipants();

  Participants getParticipantById(Long id);

  @Transactional
  Participants updateInGame(Participants participants, Boolean bool);

  List<Participants> getParticipantsByInGame(Long gameId);

  List<Participants> getParticipantsByGame(Long gameId);
}
