package com.go.tiny.rest.exception;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.rest.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GoTinyExceptionHandler {

  @ExceptionHandler(GoTinyDomainException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Status> handleGoTinyException(RuntimeException exception) {
    return new ResponseEntity<>(
        Status.builder().status(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
