package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserById(Long id);
  Optional<User> findByEmailAndActiveTrue(String email);

  Optional<User> findByUsernameAndActiveTrue(String name);

  Optional<User> findByIdAndActiveTrue(Long id);

  List<User> findAll();

  Optional<User> findByEmail(String mail);

  @Query("select u from User u order by u.userStatistics.totalPoints DESC")
  Page<User> findTop20ByTotalPoints(Pageable pageable);

  @Query("select u from User u order by u.userStatistics.gamesWon DESC")
  Page<User> findTop20ByGamesWon(Pageable pageable);

  @Query("select u from User u order by u.userStatistics.totalGames DESC")
  Page<User> findTop20ByTotalGames(Pageable pageable);

  @Query("select u from User u order by u.userStatistics.pointAverage DESC")
  Page<User> findTop20ByPointAverage(Pageable pageable);
}
