package com.example.SHA256Database;

class InputTooLargeException extends RuntimeException {
  public InputTooLargeException (int maxSize) {
    super(String.format("Input must be less than %d bytes.", maxSize));
  }
}
