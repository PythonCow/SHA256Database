package com.example.SHA256Database;

import java.lang.IllegalArgumentException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class IllegalArgumentAdvice {

  @ResponseBody
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String hashNotFoundHandler(IllegalArgumentException e) {
    return e.getMessage();
  }
}
