package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Message;
import com.zaheer.quizbackend.repos.MessageRepository;
import com.zaheer.quizbackend.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageServiceImpl extends BaseService implements MessageService {

  private final MessageRepository messageRepository;

  @Override
  @Transactional
  public Message createMessage(Message message) {
    message.setSentAt(LocalDateTime.now());
    return messageRepository.saveAndFlush(message);
  }

  @Override
  public List<Message> getLast35Messages() {
    return messageRepository.getLast35Messages(PageRequest.of(0, 35)).getContent();
  }
}
