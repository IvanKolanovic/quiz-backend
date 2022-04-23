package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {}
