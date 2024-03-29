package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participant, Long> {
  List<Participant> findAllByGameIdAndInGameTrue(Long gameId);

  List<Participant> findAllByGameIdAndHasLeftFalse(Long gameId);

  List<Participant> findAllByGameIdAndHasLeftFalseAndInGameTrue(Long gameId);

  Participant findByUserIdAndGameId(Long id, Long gameId);

  Participant findByUserIdAndGameIdAndInGameTrue(Long id, Long gameId);
}
