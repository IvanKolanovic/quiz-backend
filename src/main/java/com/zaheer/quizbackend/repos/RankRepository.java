package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {

  List<Rank> findAllByOrderByIdAsc();
}
