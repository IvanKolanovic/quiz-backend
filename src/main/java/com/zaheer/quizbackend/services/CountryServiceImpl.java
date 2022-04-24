package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Country;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.repos.CountryRepository;
import com.zaheer.quizbackend.repos.UserRepository;
import com.zaheer.quizbackend.services.interfaces.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryServiceImpl extends BaseService implements CountryService {

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Country createCountry(Country country) {
        return countryRepository.saveAndFlush(country);
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository
                .findById(id)
                .orElseThrow(resourceNotFound("Country with id: " + id + " was not found."));
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    @Transactional
    public Country updateCountry(Long id, Country input) {
        return countryRepository
                .findById(id)
                .map(
                        country -> {
                            country.setDescription(input.getDescription());
                            country.setContinent(input.getContinent());
                            country.setName(input.getName());
                            country.setCapital(input.getCapital());
                            country.setNameAbbr(input.getNameAbbr());
                            return country;
                        })
                .orElseThrow(resourceNotFound("Country with id: " + id + " was not found."));
    }

    @Override
    public Integer getNumOfCountries() {
        return countryRepository.findAll().size();
    }

    @Override
    public User getCountryByName(String countryName, Long userID) {
        Long learningIndex = countryRepository
                .findByNameContainingIgnoreCase(countryName)
                .orElseThrow(resourceNotFound("Country with name: " + countryName + " was not found.")).getId();

        User updatedUser = userRepository.findByIdAndActiveTrue(userID)
                .orElseThrow(resourceNotFound("User with id: " + userID + " was not found."));
        updatedUser.setLearningIndex(Math.toIntExact(learningIndex));

        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    @Transactional
    public void deleteCountry(Long id) {
        countryRepository.delete(getCountry(id));
    }
}
