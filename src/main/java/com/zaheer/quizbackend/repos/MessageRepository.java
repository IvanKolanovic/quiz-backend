package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  @Query("select m from Message m order by m.sentAt ASC")
  Page<Message> getLast35Messages(Pageable pageable);
}
