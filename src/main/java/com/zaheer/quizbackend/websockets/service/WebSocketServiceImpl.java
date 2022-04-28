package com.zaheer.quizbackend.websockets.service;

import com.zaheer.quizbackend.models.SocketRequestType;
import com.zaheer.quizbackend.models.db.*;
import com.zaheer.quizbackend.repos.GameRepository;
import com.zaheer.quizbackend.repos.ParticipantsRepository;
import com.zaheer.quizbackend.repos.QuestionChoicesRepository;
import com.zaheer.quizbackend.repos.QuestionRepository;
import com.zaheer.quizbackend.services.BaseService;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.services.interfaces.ParticipantsService;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
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
  private final GameRepository gameRepository;
  private final ParticipantsRepository participantsRepository;
  private final QuestionRepository questionRepository;
  private final QuestionChoicesRepository questionChoicesRepository;

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
  public WebsocketPayload<Game> joinGame(UserGame input) {
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
  public WebsocketPayload<List<Participants>> startGame(Game game) {
    List<Participants> participants = participantsService.getParticipantsByGame(game.getId());
    log.info("{}", participants.size());
    return gameService.startGame(game, participants);
  }

  @Override
  @Transactional
  public WebsocketPayload<List<Question>> prepareQuestions(Game game) {
    gameService.prepareQuestions(game);
    List<Question> questions = questionRepository.findAllByGameIdOrderByIdAsc(game.getId());
    questions.forEach(
        question ->
            question.setQuestionChoices(
                questionChoicesRepository.findAllByQuestionIdOrderByIdAsc(question.getId())));
    List<Participants> participants = participantsService.getParticipantsByGame(game.getId());
    return WebsocketPayload.<List<Question>>builder()
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
  public WebsocketPayload<List<Participants>> finishedGame(UserGame userGame) {
    Game game = userGame.getGame();
    List<Participants> participants = participantsService.getParticipantsByGame(game.getId());

    game.setPlayers(game.getPlayers() - 1);
    game = gameRepository.saveAndFlush(game);
    participants.forEach(
        participant -> {
          if (participant.getUser().getId().equals(userGame.getUser().getId())) {
            participant.setInGame(false);
            participantsRepository.saveAndFlush(participant);
          }
        });

    if (game.getPlayers() != 0) {
      return null;
    }

    game.setActive(false);
    gameRepository.saveAndFlush(game);

    return WebsocketPayload.<List<Participants>>builder()
        .users(participants.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Evaluate_Answer)
        .time(LocalDateTime.now())
        .content(participants)
        .build();
  }

  @Override
  @Transactional
  public WebsocketPayload<Participants> leaveLiveGame(UserGame payload) {

    Participants participant =
        participantsRepository.findByUserIdAndGameId(
            payload.getUser().getId(), payload.getGame().getId());
    participant = gameService.leaveLiveGameRoom(participant);
    List<Participants> receivers =
        participantsService.getParticipantsByGame(payload.getGame().getId());

    return WebsocketPayload.<Participants>builder()
        .users(receivers.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Left_Game)
        .time(LocalDateTime.now())
        .content(participant)
        .build();
  }
}
