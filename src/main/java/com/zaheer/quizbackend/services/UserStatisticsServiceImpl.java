package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.UserStatistics;
import com.zaheer.quizbackend.repos.RankRepository;
import com.zaheer.quizbackend.repos.UserStatisticsRepository;
import com.zaheer.quizbackend.services.interfaces.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatisticsServiceImpl extends BaseService implements UserStatisticsService {

  private final UserStatisticsRepository userStatisticsRepository;
  private final RankRepository rankRepository;

  @Override
  @Transactional
  public UserStatistics createStatistic() {
    return userStatisticsRepository.saveAndFlush(
        UserStatistics.builder()
            .totalPoints(0)
            .gamesWon(0)
            .rank(
                rankRepository
                    .findById(1L)
                    .orElseThrow(resourceNotFound("Rank with id: " + 1 + " was not found.")))
            .build());
  }

  @Override
  public UserStatistics getStatistic(Long id) {
    return userStatisticsRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Statistic with id: " + id + " was not found."));
  }

  @Override
  public UserStatistics getStatisticByUserId(Long userId) {
    return userStatisticsRepository
        .findByUserId(userId)
        .orElseThrow(resourceNotFound("Statistic with user id: " + userId + " was not found."));
  }

  @Override
  @Transactional
  public List<UserStatistics> getAllStatistics() {
    return userStatisticsRepository.findAllByOrderByIdAsc();
  }

  @Override
  @Transactional
  public UserStatistics updateStatistic(Long id, UserStatistics input) {

    return userStatisticsRepository
        .findByUserId(id)
        .map(
            statistic -> {
              statistic.setTotalPoints(input.getTotalPoints());
              statistic.setGamesWon(input.getGamesWon());
              statistic.setRank(input.getRank());

              return statistic;
            })
        .orElseThrow(resourceNotFound("Statistic with user id: " + id + " was not found."));
  }
}
