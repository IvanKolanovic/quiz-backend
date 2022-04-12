package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Rank;
import com.zaheer.quizbackend.repos.RankRepository;
import com.zaheer.quizbackend.services.interfaces.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RankServiceImpl extends BaseService implements RankService {

  private final RankRepository rankRepository;

  @Override
  public List<Rank> getAllRanks() {
    return rankRepository.findAllByOrderByIdAsc();
  }

  @Override
  public Rank getRankById(Long id) {
    return rankRepository
        .findById(id)
        .orElseThrow(resourceNotFound("Rank with id: " + id + " was not found"));
  }
}
