package com.zaheer.quizbackend.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = "com.zaheer")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = "Malformed JSON request";
    return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String error = "Request is missing a path variable";
    log.error(error, ex);
    return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = "Request parameters are not valid.";
    log.error(error, ex);
    return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = "A fatal binding error has occurred.";
    log.error(error, ex);
    return buildResponseEntity(new ApiError(status, error, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = "Failed to process multipart/form-data request.";
    log.error(error, ex);
    return buildResponseEntity(new ApiError(status, error, ex));
  }

  @ExceptionHandler(BadCredentialsException.class)
  protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
    ApiError apiError = new ApiError(UNAUTHORIZED, "Incorrect username or password!", ex);
    log.error("Incorrect username or password!", ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(ResourceNotFoundException ex) {
    ApiError apiError = new ApiError(NOT_FOUND, ex.getMessage(), ex);
    log.info(ex.getMessage(), ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleNoPermission(AccessDeniedException ex) {
    ApiError apiError = new ApiError(UNAUTHORIZED, ex.getMessage(), ex);
    log.info(ex.getMessage(), ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(RequestFailedException.class)
  protected ResponseEntity<Object> handleRequestFailed(RequestFailedException ex) {
    ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex);
    log.error(ex.getMessage(), ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgument(RequestFailedException ex) {
    ApiError apiError = new ApiError(ex.getStatus(), "Illegal arguments.", ex);
    log.error(ex.getMessage(), ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(DateTimeParseException.class)
  protected ResponseEntity<Object> handleBadTieFormatting(DateTimeParseException ex) {
    ApiError apiError = new ApiError(BAD_REQUEST, "Bad Date/DateTime input format.", ex);
    log.error(ex.getMessage(), ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(IllegalAccessException.class)
  protected ResponseEntity<Object> handleReflection(IllegalAccessException ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, "Internal reflection error.", ex);
    log.error(ex.getMessage(), ex);
    return buildResponseEntity(apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
