package com.zaheer.quizbackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RequestFailedException extends RuntimeException {

  private HttpStatus status;

  public RequestFailedException(String message) {
    super(message);
  }

  public RequestFailedException(String message, Throwable obj) {
    super(message, obj);
  }

  public RequestFailedException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }
}
