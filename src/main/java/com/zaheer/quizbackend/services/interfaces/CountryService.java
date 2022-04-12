package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Country;

import java.util.List;

public interface CountryService {
  Country createCountry(Country country);

  Country getCountry(Long id);

  List<Country> getAllCountries();

  Country updateCountry(Long id, Country input);

  void deleteCountry(Long id);
}
