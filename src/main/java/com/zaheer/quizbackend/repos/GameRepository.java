package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {}
