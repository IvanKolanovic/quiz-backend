package com.zaheer.quizbackend.websockets.service.interfaces;

import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import org.springframework.transaction.annotation.Transactional;

public interface WebSocketService {

    @Transactional
    WebsocketPayload<String> connected(User user);
}
