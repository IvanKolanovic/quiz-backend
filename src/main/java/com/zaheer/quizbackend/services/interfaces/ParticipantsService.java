package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Participant;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParticipantsService {
  @Transactional
  Participant create(Participant participants);

  List<Participant> getParticipants();

  Participant getParticipant(Long gameId, Long userId);

  @Transactional
  Participant updateInGame(Participant participants, Boolean bool);

  List<Participant> getParticipantsByInGame(Long gameId);

  List<Participant> getParticipantsByGame(Long gameId);
}
