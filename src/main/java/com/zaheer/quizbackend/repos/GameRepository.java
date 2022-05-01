package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
  Optional<Game> findByNameAndActiveTrue(String name);

  Optional<Game> findByIdAndActiveTrue(Long id);

  List<Game> findAllByActiveTrueAndPlayers(Integer num);

  List<Game> findAllByActiveTrue();

  @Query("select g from Game g where g.active = false and g.startedAt > ?1")
  List<Game> findGamesForDelete(LocalDateTime nowWith40seconds);

  List<Game> findAllByActiveTrueAndStartedFalse();
}
