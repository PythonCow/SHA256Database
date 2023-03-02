package com.example.SHA256Database;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class HashNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(HashNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String hashNotFoundHandler(HashNotFoundException e) {
    return e.getMessage();
  }
}
