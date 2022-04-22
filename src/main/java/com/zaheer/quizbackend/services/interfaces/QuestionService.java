package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionService {

    @Transactional
    Question createQuestion(Question question);

    Question getQuestion(Long id);

    List<Question> getAllQuestionsByGame(Long gameId);
}
