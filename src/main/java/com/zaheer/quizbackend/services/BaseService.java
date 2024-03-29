package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.exceptions.ResourceNotFoundException;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.security.MyUserDetails;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Getter
@Slf4j
public class BaseService {

  protected Supplier<RuntimeException> resourceNotFound(String str) {
    return () -> new ResourceNotFoundException(str);
  }

  protected Long getCurrentUserId() {
    return getCurrentUser().getId();
  }

  protected User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return ((MyUserDetails) authentication.getPrincipal()).getUser();
  }

  public long randomNumberBetween(int lowerExclusive, int upperInclusive) {
    return ThreadLocalRandom.current().nextLong(lowerExclusive, upperInclusive);
  }
}
