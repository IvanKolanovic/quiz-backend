package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Country;
import com.zaheer.quizbackend.repos.CountryRepository;
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
  @Transactional
  public void deleteCountry(Long id) {
    countryRepository.delete(getCountry(id));
  }
}
