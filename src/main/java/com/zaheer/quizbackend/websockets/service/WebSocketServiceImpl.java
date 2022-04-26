package com.zaheer.quizbackend.websockets.service;

import com.zaheer.quizbackend.models.SocketRequestType;
import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Participants;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.db.UserAnswer;
import com.zaheer.quizbackend.services.BaseService;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.services.interfaces.ParticipantsService;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.GameQuestion;
import com.zaheer.quizbackend.websockets.models.generics.JoinGame;
import com.zaheer.quizbackend.websockets.service.interfaces.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl extends BaseService implements WebSocketService {

  private final GameService gameService;
  private final ParticipantsService participantsService;

  @Override
  @Transactional
  public WebsocketPayload<String> connected(User user) {
    log.info("=== User Connected: {}", user);
    return WebsocketPayload.<String>builder()
        .users(List.of(user))
        .type(SocketRequestType.Connected)
        .time(LocalDateTime.now())
        .content("Welcome " + user.getUsername() + " to Flaginator. Enjoy!")
        .build();
  }

  @Override
  @Transactional
  public WebsocketPayload<Game> joinGame(JoinGame input) {
    Game game = gameService.joinGame(input);
    log.info("=== Joined game: {}", game);
    return WebsocketPayload.<Game>builder()
        .users(
            participantsService.getParticipantsByGame(game.getId()).stream()
                .map(Participants::getUser)
                .collect(Collectors.toList()))
        .type(SocketRequestType.Join_Game)
        .time(LocalDateTime.now())
        .content(game)
        .build();
  }

  @Override
  @Transactional
  public WebsocketPayload<List<Participants>> startGame(WebsocketPayload<Game> payload) {
    Game game = gameService.startGame(payload.getContent());

    List<Participants> participants = participantsService.getParticipantsByGame(game.getId());
    return WebsocketPayload.<List<Participants>>builder()
        .users(participants.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Start_Game)
        .time(LocalDateTime.now())
        .content(participants)
        .build();
  }

  @Override
  @Transactional
  public WebsocketPayload<List<GameQuestion>> prepareQuestions(Game game) {
    List<GameQuestion> questions = gameService.prepareQuestions(game);

    List<Participants> participants = participantsService.getParticipantsByGame(game.getId());
    return WebsocketPayload.<List<GameQuestion>>builder()
        .users(participants.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Game_Questions)
        .time(LocalDateTime.now())
        .content(questions)
        .build();
  }

  @Override
  @Transactional
  public WebsocketPayload<EvaluatedAnswer> evaluateAnswer(UserAnswer userAnswer) {
    EvaluatedAnswer evaluatedAnswer = gameService.evaluateUserAnswer(userAnswer);

    List<Participants> participants =
        participantsService.getParticipantsByGame(userAnswer.getGame().getId());
    return WebsocketPayload.<EvaluatedAnswer>builder()
        .users(participants.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Evaluate_Answer)
        .time(LocalDateTime.now())
        .content(evaluatedAnswer)
        .build();
  }

  @Override
  @Transactional
  public WebsocketPayload<Participants> leaveLiveGame(Participants payload) {
    Participants participants = gameService.leaveLiveGameRoom(payload);
    List<Participants> receivers =
        participantsService.getParticipantsByGame(payload.getGame().getId());

    return WebsocketPayload.<Participants>builder()
        .users(receivers.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Left_Game)
        .time(LocalDateTime.now())
        .content(participants)
        .build();
  }
}
