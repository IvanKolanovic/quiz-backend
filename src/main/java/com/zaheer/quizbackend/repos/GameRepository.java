package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
  Optional<Game> findByNameAndActiveTrue(String name);

  Optional<Game> findByIdAndActiveTrue(Long id);

  List<Game> findAllByActiveTrue();
}
