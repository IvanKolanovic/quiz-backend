package com.zaheer.quizbackend.models.db;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "message")
@Entity
@Builder
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

  @Lob @Column private String content;
}
