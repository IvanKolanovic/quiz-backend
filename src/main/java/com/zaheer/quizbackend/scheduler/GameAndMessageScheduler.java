package com.zaheer.quizbackend.scheduler;

import com.zaheer.quizbackend.models.db.Message;
import com.zaheer.quizbackend.repos.GameRepository;
import com.zaheer.quizbackend.repos.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GameAndMessageScheduler {

  private final GameRepository gameRepository;
  private final MessageRepository messageRepository;

  /*@Scheduled(fixedDelay = 15000, initialDelay = 1000)
  public void deleteGames() {
    List<Game> games = gameRepository.findGamesForDelete(LocalDateTime.now().plusSeconds(40L));
          gameRepository.deleteAll(games);

  }*/

  @Scheduled(fixedDelay = 15000, initialDelay = 1000)
  public void deleteMessages() {
    List<Message> messages = messageRepository.forDelete(PageRequest.of(0,35)).getContent();
    if (messages.size() < 35) return;
    messageRepository.deleteAll(messages);
  }
}
