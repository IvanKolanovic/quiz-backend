package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
  List<Participants> findAllByGameId(Long gameId);

  Participants findByUserId(Long id);

  Participants findByUserIdAndGameId(Long id,Long gameId);
}
