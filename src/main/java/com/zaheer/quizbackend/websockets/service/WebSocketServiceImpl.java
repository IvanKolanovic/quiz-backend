package com.zaheer.quizbackend.websockets.service;

import com.zaheer.quizbackend.models.enums.SocketRequestType;
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
import org.springframework.transaction.annotation.Isolation;
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
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<String> connected(User user) {
    return WebsocketPayload.<String>builder()
        .users(List.of(user))
        .type(SocketRequestType.Connected)
        .time(LocalDateTime.now())
        .content("Welcome " + user.getUsername() + " to Flaginator. Enjoy!")
        .build();
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<Game> joinGame(UserGame input) {
    Game game = gameService.joinGame(input);
    return WebsocketPayload.<Game>builder()
        .users(
            participantsService.getParticipantsByGame(game.getId()).stream()
                .map(Participant::getUser)
                .collect(Collectors.toList()))
        .type(SocketRequestType.Join_Game)
        .time(LocalDateTime.now())
        .content(game)
        .build();
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<List<Participant>> startGame(Game game) {
    List<Participant> participants = participantsService.getParticipantsByGame(game.getId());
    return gameService.startGame(game, participants);
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<List<Question>> prepareQuestions(Game game) {
    gameService.prepareQuestions(game);
    List<Question> questions = questionRepository.findAllByGameIdOrderByIdAsc(game.getId());
    questions.forEach(
        question ->
            question.setQuestionChoices(
                questionChoicesRepository.findAllByQuestionIdOrderByIdAsc(question.getId())));
    List<Participant> participants = participantsService.getParticipantsByInGame(game.getId());
    return WebsocketPayload.<List<Question>>builder()
        .users(participants.stream().map(Participant::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Game_Questions)
        .time(LocalDateTime.now())
        .content(questions)
        .build();
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<EvaluatedAnswer> evaluateAnswer(UserAnswer userAnswer) {
    EvaluatedAnswer evaluatedAnswer = gameService.evaluateUserAnswer(userAnswer);
    List<Participant> participants =
        participantsService.getParticipantsByInGame(userAnswer.getGame().getId());
    return WebsocketPayload.<EvaluatedAnswer>builder()
        .users(participants.stream().map(Participant::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Evaluate_Answer)
        .time(LocalDateTime.now())
        .content(evaluatedAnswer)
        .build();
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<List<Participant>> finishedGame2(Long gameId, Long userId) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ignored) {
      log.error("Jebem ti Thread i Deadlock mrtvi");
    }

    Participant participant = participantsService.getParticipant(gameId, userId);
    Game game = gameService.get(gameId);

    participantsService.updateInGame(participant, false);
    List<Participant> inGameParticipants =
        participantsService.getParticipantsByInGame(game.getId());
    // check if one player is still playing
    if (inGameParticipants.size() != 0) {
      return null;
    }

    game.setActive(false);
    game.setPlayers(inGameParticipants.size());
    List<Participant> participants = participantsService.getParticipantsByGame(gameId);

    if (participants.get(0).getUserScore() > participants.get(1).getUserScore()) {
      participants.get(0).setHasWon(true);
      participants.get(1).setHasWon(false);
    } else if (participants.get(0).getUserScore() < participants.get(1).getUserScore()) {
      participants.get(1).setHasWon(true);
      participants.get(0).setHasWon(false);
    }

    participants.forEach(gameService::applyScore);
    return WebsocketPayload.<List<Participant>>builder()
        .users(participants.stream().map(Participant::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Finished_Game)
        .time(LocalDateTime.now())
        .content(participants)
        .build();
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public WebsocketPayload<Participant> leaveLiveGame(UserGame payload) {
    Participant participant =
        participantsRepository.findByUserIdAndGameId(
            payload.getUser().getId(), payload.getGame().getId());
    participant = gameService.leaveLiveGameRoom(participant, payload.getGame());
    List<Participant> receivers =
        participantsService.getParticipantsByInGame(payload.getGame().getId());

    return WebsocketPayload.<Participant>builder()
        .users(receivers.stream().map(Participant::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Left_Game)
        .time(LocalDateTime.now())
        .content(participant)
        .build();
  }
}
