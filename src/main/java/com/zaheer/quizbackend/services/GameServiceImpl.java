package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.SocketRequestType;
import com.zaheer.quizbackend.models.db.*;
import com.zaheer.quizbackend.repos.*;
import com.zaheer.quizbackend.services.interfaces.CountryService;
import com.zaheer.quizbackend.services.interfaces.GameService;
import com.zaheer.quizbackend.services.interfaces.UserStatisticsService;
import com.zaheer.quizbackend.websockets.models.WebsocketPayload;
import com.zaheer.quizbackend.websockets.models.generics.EvaluatedAnswer;
import com.zaheer.quizbackend.websockets.models.generics.UserGame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl extends BaseService implements GameService {

  private final GameRepository gameRepository;
  private final ParticipantsServiceImpl participantsService;
  private final ParticipantsRepository participantsRepository;
  private final CountryService countryService;
  private final CountryRepository countryRepository;
  private final QuestionRepository questionRepository;
  private final QuestionChoicesRepository questionChoicesRepository;
  private final UserAnswerRepository userAnswerRepository;
  private final UserStatisticsService userStatisticsService;

  @Override
  public Game create(Game game) {
    if (isNameInUse(game.getName()))
      throw new RequestFailedException(
          HttpStatus.BAD_REQUEST, "Game name: " + game.getName() + " is taken.");

    game.setLocked(Optional.ofNullable(game.getPassword()).isPresent());
    game.setActive(true);
    game.setStarted(false);
    game.setPlayers(0);
    return gameRepository.saveAndFlush(game);
  }

  @Override
  public Game get(Long id) {
    return gameRepository
        .findByIdAndActiveTrue(id)
        .orElseThrow(resourceNotFound("Game with id: " + id + " was not found."));
  }

  @Override
  public List<Game> getAll() {
    return gameRepository.findAllByActiveTrueAndStartedFalse();
  }

  @Override
  @Transactional
  public Game joinGame(UserGame input) {
    Game myGame = input.getGame();
    Game g =
        gameRepository
            .findByIdAndActiveTrue(myGame.getId())
            .map(
                game -> {
                  if (game.getPlayers().equals(2))
                    throw new RequestFailedException(HttpStatus.CONFLICT, "Game is full.");
                  if (myGame.getName().equals(game.getName())) {
                    if (Optional.ofNullable(myGame.getPassword()).isPresent()) {
                      if (myGame.getPassword().equals(game.getPassword()))
                        game.setPlayers(game.getPlayers() + 1);
                      else throw new RequestFailedException(HttpStatus.CONFLICT, "Wrong password.");
                    } else game.setPlayers(game.getPlayers() + 1);
                  } else {
                    throw new RequestFailedException(HttpStatus.CONFLICT, "Wrong game name.");
                  }
                  return game;
                })
            .orElseThrow(resourceNotFound("Game with id: " + myGame.getId() + " was not found."));

    Participants p = Participants.builder().user(input.getUser()).game(g).build();
    participantsService.create(p);

    return gameRepository.saveAndFlush(g);
  }

  @Override
  @Transactional
  public WebsocketPayload<List<Participants>> startGame(
      Game game, List<Participants> participants) {
    log.info("Start game u game service");
    game.setStarted(true);
    participants =
        participants.stream()
            .map(participant -> participantsService.updateInGame(participant, true))
            .collect(Collectors.toList());
    gameRepository.saveAndFlush(game);
    return WebsocketPayload.<List<Participants>>builder()
        .users(participants.stream().map(Participants::getUser).collect(Collectors.toList()))
        .type(SocketRequestType.Start_Game)
        .time(LocalDateTime.now())
        .content(participants)
        .build();
  }

  @Override
  @Transactional
  public void checkAndUpdateGameStatus(Game input) {
    if (input.getPlayers() == 0 && input.getStarted()) {
      input.setActive(false);
      gameRepository.saveAndFlush(input);
    }
  }

  @Override
  @Transactional
  public Participants leaveLiveGameRoom(Participants participant) {
    Game game = get(participant.getId());

    participant.setUserScore(0);
    participant.setInGame(false);
    game.setPlayers(game.getPlayers() - 1);

    gameRepository.saveAndFlush(game);
    participantsRepository.saveAndFlush(participant);
    checkAndUpdateGameStatus(game);
    return participant;
  }

  @Override
  @Transactional
  public void prepareQuestions(Game payload) {
    List<Question> questions = questionRepository.saveAllAndFlush(createQuestions(payload));
    questionChoicesRepository.saveAllAndFlush(
        questions.stream()
            .map(this::createChoices)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()));
  }

  @Override
  @Transactional
  public List<Question> createQuestions(Game game) {

    List<Question> questions = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      long rand = randomNumberBetween(1, countryService.getNumOfCountries() + 1);
      questions.add(
          Question.builder()
              .game(game)
              .abbr(countryService.getCountry(rand).getNameAbbr())
              .build());
    }
    return questions;
  }

  @Override
  @Transactional
  public List<QuestionChoices> createChoices(Question question) {

    List<QuestionChoices> choices = new ArrayList<>();
    choices.add(
        QuestionChoices.builder()
            .question(question)
            .choice(countryRepository.findByNameAbbr(question.getAbbr()))
            .isRight(true)
            .build());

    for (int i = 0; i < 3; i++) {
      long rand = randomNumberBetween(1, countryService.getNumOfCountries() + 1);

      Country choice = countryService.getCountry(rand);

      while (choice.getNameAbbr().equals(question.getAbbr()))
        choice = countryService.getCountry(rand);

      choices.add(
          QuestionChoices.builder().question(question).choice(choice).isRight(false).build());
    }
    return questionChoicesRepository.saveAllAndFlush(choices);
  }

  @Override
  @Transactional
  public EvaluatedAnswer evaluateUserAnswer(UserAnswer userAnswer) {
    userAnswer.setAnswerTime(LocalDateTime.now());
    userAnswer.setIsRight(userAnswer.getChoice().getIsRight());

    Participants participants =
        participantsRepository.findByUserIdAndGameId(
            userAnswer.getUser().getId(), userAnswer.getGame().getId());
    if (userAnswer.getIsRight()) participants.updateScore(50);

    UserAnswer u = userAnswerRepository.saveAndFlush(userAnswer);
    participants = participantsRepository.saveAndFlush(participants);

    return new EvaluatedAnswer(participants, u);
  }

  @Override
  @Transactional
  public void applyScore(Participants participant) {
    UserStatistics userStatistics = participant.getUser().getUserStatistics();
    userStatisticsService.updateStatistic(userStatistics.getId(), userStatistics);
  }

  @Override
  public boolean isNameInUse(String name) {
    Optional<Game> game = gameRepository.findByNameAndActiveTrue(name);
    return game.isPresent();
  }
}
