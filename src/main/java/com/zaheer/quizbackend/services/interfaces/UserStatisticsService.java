package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.UserStatistics;

import java.util.List;

public interface UserStatisticsService {
  UserStatistics createStatistic();

  UserStatistics getStatistic(Long id);

  UserStatistics getStatisticByUserId(Long userId);

  List<UserStatistics> getAllStatistics();

  UserStatistics updateStatistic(Long id, UserStatistics input);
}
