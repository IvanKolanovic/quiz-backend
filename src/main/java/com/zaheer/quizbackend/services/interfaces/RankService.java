package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Rank;

import java.util.List;

public interface RankService {

  List<Rank> getAllRanks();

  Rank getRankById(Long id);
}
