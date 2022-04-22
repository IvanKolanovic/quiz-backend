package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.UserAnswer;
import com.zaheer.quizbackend.repos.UserAnswerRepository;
import com.zaheer.quizbackend.services.interfaces.UserAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAnswerServiceImpl extends BaseService implements UserAnswerService {

  private final UserAnswerRepository userAnswerRepository;

  @Override
  @Transactional
  public UserAnswer createUserAnswer(UserAnswer userAnswer) {
    userAnswer.setIsRight(userAnswer.getIsRight());
    userAnswer.setAnswerTime(LocalDateTime.now());
    return userAnswerRepository.saveAndFlush(userAnswer);
  }

  @Override
  public UserAnswer getUserAnswer(Long id) {
    return userAnswerRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Answer with id: " + id + " was not found."));
  }

  @Override
  public List<UserAnswer> getAllAnswersByGame(Long gameId) {
    return userAnswerRepository.findAllByGameIdOrderByIdAsc(gameId);
  }
}
