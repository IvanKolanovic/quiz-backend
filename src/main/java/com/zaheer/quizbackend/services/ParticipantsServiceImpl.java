package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Participant;
import com.zaheer.quizbackend.repos.ParticipantsRepository;
import com.zaheer.quizbackend.services.interfaces.ParticipantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantsServiceImpl extends BaseService implements ParticipantsService {

  private final ParticipantsRepository participantsRepository;

  @Override
  @Transactional
  public Participant create(Participant participants) {
    participants.setUserScore(0);
    participants.setInGame(false);
    participants.setHasWon(null);
    return participantsRepository.saveAndFlush(participants);
  }

  @Override
  public List<Participant> getParticipants() {
    return participantsRepository.findAll();
  }

  @Override
  public Participant getParticipant(Long gameId, Long userId) {
    return participantsRepository.findByUserIdAndGameId(userId, gameId);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.SERIALIZABLE)
  public Participant updateInGame(Participant participants, Boolean bool) {
    participants.setFinishedAt(LocalDateTime.now());
    participants.setInGame(bool);
    return participantsRepository.save(participants);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public List<Participant> getParticipantsByInGame(Long gameId) {
    return participantsRepository.findAllByGameIdAndInGameTrue(gameId);
  }

  @Override
  public List<Participant> getParticipantsByGame(Long gameId) {
    return participantsRepository.findAllByGameId(gameId);
  }
}
