package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.QuestionChoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionChoicesRepository extends JpaRepository<QuestionChoices, Long> {

    List<QuestionChoices> findAllByQuestionIdOrderByIdAsc(Long id);
}
