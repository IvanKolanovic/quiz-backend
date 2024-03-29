package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  @Query("select m from Message m order by m.sentAt desc")
  Page<Message> getLast35Messages(Pageable pageable);

  @Query("select m from Message m order by m.sentAt asc")
  Page<Message> forDelete(Pageable pageable);

}
