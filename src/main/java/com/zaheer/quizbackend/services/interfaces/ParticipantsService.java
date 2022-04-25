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
  Participants updateInGame(Long id, Boolean bool);

  List<Participants> getParticipantsByGame(Long gameId);
}
