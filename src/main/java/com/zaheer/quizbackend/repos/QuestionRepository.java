package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {}
