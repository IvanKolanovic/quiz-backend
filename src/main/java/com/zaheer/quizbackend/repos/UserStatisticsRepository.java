package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
  List<UserStatistics> findAllByOrderByIdAsc();
}
