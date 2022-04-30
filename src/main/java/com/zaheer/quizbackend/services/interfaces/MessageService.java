package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageService {
    @Transactional
    Message createMessage(Message message);

    List<Message> getLast35Messages();
}
