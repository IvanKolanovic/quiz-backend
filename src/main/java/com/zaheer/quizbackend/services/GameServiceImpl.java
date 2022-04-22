package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Country;
import com.zaheer.quizbackend.models.db.Game;
import com.zaheer.quizbackend.models.db.Question;
import com.zaheer.quizbackend.repos.CountryRepository;
import com.zaheer.quizbackend.repos.GameRepository;
import com.zaheer.quizbackend.repos.QuestionRepository;
import com.zaheer.quizbackend.services.interfaces.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl extends BaseService implements GameService {

    private final GameRepository gameRepository;
    private final CountryRepository countryRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Game create(Game game) {
        game.setPlayerOneScore(0);
        game.setPlayerTwoScore(0);

        List<Country> allCountries = countryRepository.findAll();

        List<Question> allQuestions = questionRepository.findAll();
        List<Question> questionsList = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < allCountries.size(); i++) {
            int rand = random.nextInt(allQuestions.size());
            questionsList.add(allQuestions.get(rand));
            allQuestions.remove(rand);
        }

        return gameRepository.saveAndFlush(game);
    }

    @Override
    public Game get(Long id) {
        return gameRepository
                .findById(id)
                .orElseThrow(resourceNotFound("Game with id: " + id + " was not found."));
    }

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game updateScore(Long id, Game input) {
        return null;
    }
}
