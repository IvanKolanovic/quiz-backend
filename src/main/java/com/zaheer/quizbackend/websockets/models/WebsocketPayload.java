package com.zaheer.quizbackend.websockets.models;

import com.zaheer.quizbackend.models.db.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WebsocketPayload<T> {

  private LocalDateTime time;
  private User user;
  private T content;
}
