package com.zaheer.quizbackend.websockets.models;

import com.zaheer.quizbackend.models.db.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WebsocketPayload<T> {

  private User client;
  private T data;
}
