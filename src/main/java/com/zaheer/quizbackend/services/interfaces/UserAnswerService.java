package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.UserAnswer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAnswerService {
  @Transactional
  UserAnswer createUserAnswer(UserAnswer userAnswer);

  UserAnswer getUserAnswer(Long id);

  List<UserAnswer> getAllAnswersByGame(Long gameId);
}
