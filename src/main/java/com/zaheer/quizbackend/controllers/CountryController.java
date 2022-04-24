package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.models.db.Country;
import com.zaheer.quizbackend.models.security.annotations.isAdmin;
import com.zaheer.quizbackend.services.interfaces.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class CountryController {

  private final CountryService countryService;

  @isAdmin
  @PostMapping("/country")
  public ResponseEntity<Object> createCountry(@RequestBody Country country) {
    return ResponseEntity.ok(countryService.createCountry(country));
  }

  @GetMapping("/country/{id}")
  public ResponseEntity<Object> getCountry(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(countryService.getCountry(id));
  }

  @GetMapping("/country")
  public ResponseEntity<Object> getCountryByName(@RequestBody Country country) {
    return ResponseEntity.ok(countryService.getCountryByName(country));
  }

  @GetMapping("/countries")
  public ResponseEntity<Object> getAllCountries() {
    return ResponseEntity.ok(countryService.getAllCountries());
  }

  @GetMapping("/countries/num")
  public ResponseEntity<Object> getNumOfCountries() {
    return ResponseEntity.ok(countryService.getNumOfCountries());
  }

  @PutMapping("/country/{id}")
  public ResponseEntity<Object> updateCountry(
      @PathVariable(value = "id") Long id, @RequestBody Country country) {
    return ResponseEntity.ok(countryService.updateCountry(id, country));
  }

  @DeleteMapping("/country/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    countryService.deleteCountry(id);
    return ResponseEntity.ok("True");
  }
}
