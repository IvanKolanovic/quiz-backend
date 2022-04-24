package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Country;
import com.zaheer.quizbackend.models.db.User;

import java.util.List;

public interface CountryService {
  Country createCountry(Country country);

  Country getCountry(Long id);

  List<Country> getAllCountries();

  Country updateCountry(Long id, Country input);

  Integer getNumOfCountries();

  User getCountryByName(String countryName, Long userID);

  void deleteCountry(Long id);
}
