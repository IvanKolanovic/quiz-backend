package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
  List<UserAnswer> findAllByGameIdOrderByIdAsc(Long gameId);
}
