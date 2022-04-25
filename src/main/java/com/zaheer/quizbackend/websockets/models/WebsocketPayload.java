package com.zaheer.quizbackend.websockets.models;

import com.zaheer.quizbackend.models.SocketRequestType;
import com.zaheer.quizbackend.models.db.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WebsocketPayload<T> {

  private SocketRequestType type;
  private LocalDateTime time;
  private List<User> users;
  private T content;
}
