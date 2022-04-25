package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  Optional<Country> findCountriesByNameIgnoreCase(String name);

  Country findByNameAbbr(String abbr);
}
