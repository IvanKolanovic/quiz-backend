package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.QuestionChoices;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionChoicesService {
  @Transactional
  QuestionChoices createQuestionChoice(QuestionChoices questionChoices);

  QuestionChoices getChoice(Long id);

  List<QuestionChoices> getAllChoicesByQuestion(Long questionId);
}
