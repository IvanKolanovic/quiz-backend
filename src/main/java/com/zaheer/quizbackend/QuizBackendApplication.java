package com.zaheer.quizbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class QuizBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizBackendApplication.class, args);
  }
}
