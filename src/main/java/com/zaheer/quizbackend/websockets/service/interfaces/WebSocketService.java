package com.zaheer.quizbackend.websockets.service.interfaces;

import com.zaheer.quizbackend.websockets.models.WebsocketPayload;

public interface WebSocketService {
    WebsocketPayload<String> connected();
}
