package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.repos.ParticipantsRepository;
import com.zaheer.quizbackend.services.interfaces.ParticipantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantsServiceImpl extends BaseService implements ParticipantsService {

  private final ParticipantsRepository participantsRepository;

  @Override
  @Transactional
  public Participants create(Participants participants) {
    participants.setUserScore(0);
    participants.setInGame(false);
    return participantsRepository.saveAndFlush(participants);
  }

  @Override
  public List<Participants> getParticipants() {
    return participantsRepository.findAll();
  }

  @Override
  public Participants getParticipantById(Long id) {
    return participantsRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Participant with id: " + id + " was not found."));
  }

  @Override
  @Transactional
  public Participants updateInGame(Participants participants, Boolean bool) {
    participants.setInGame(bool);
    return participantsRepository.saveAndFlush(participants);
  }

  @Override
  public List<Participants> getParticipantsByGame(Long gameId) {
    return participantsRepository.findAllByGameId(gameId);
  }
}
