package com.example.SHA256Database;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class InputTooLargeAdvice {

  @ResponseBody
  @ExceptionHandler(InputTooLargeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String InputTooLargeHandler(InputTooLargeException e) {
    return e.getMessage();
  }
}
