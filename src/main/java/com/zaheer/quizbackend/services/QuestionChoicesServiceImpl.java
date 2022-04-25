package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Question;
import com.zaheer.quizbackend.models.db.QuestionChoices;
import com.zaheer.quizbackend.repos.QuestionChoicesRepository;
import com.zaheer.quizbackend.services.interfaces.CountryService;
import com.zaheer.quizbackend.services.interfaces.QuestionChoicesService;
import com.zaheer.quizbackend.services.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionChoicesServiceImpl extends BaseService implements QuestionChoicesService {

  private final QuestionChoicesRepository questionChoicesRepository;
  private final CountryService countryService;
  private final QuestionService questionService;

  @Override
  @Transactional
  public QuestionChoices createQuestionChoice(QuestionChoices questionChoices) {
    Question question = questionChoices.getQuestion();
    questionChoices.setIsRight(
        question.getAbbr().equals(questionChoices.getChoice().getNameAbbr()));
    return questionChoicesRepository.saveAndFlush(questionChoices);
  }

  @Override
  public QuestionChoices getChoice(Long id) {
    return questionChoicesRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Question Choice with id: " + id + " was not found."));
  }

  @Override
  public List<QuestionChoices> getAllChoicesByQuestion(Long questionId) {
    Question question = questionService.getQuestion(questionId);
    return questionChoicesRepository.findAllByQuestionIdOrderByIdAsc(question.getId());
  }
}
