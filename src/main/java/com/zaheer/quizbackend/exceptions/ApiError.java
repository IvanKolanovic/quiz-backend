package com.zaheer.quizbackend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {

  private int statusCode;
  private HttpStatus status;

  private String type;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;

  private String debugMessage;

  private ApiError() {
    timestamp = LocalDateTime.now();
  }

  ApiError(HttpStatus status) {
    this();
    this.status = status;
    this.statusCode = status.value();
  }

  ApiError(String message, Throwable ex) {
    this();
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  ApiError(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.statusCode = status.value();
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  ApiError(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.statusCode = status.value();
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  ApiError(HttpStatus status, String message, Throwable ex, String type) {
    this();
    this.status = status;
    this.statusCode = status.value();
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
    this.type = type;
  }
}
