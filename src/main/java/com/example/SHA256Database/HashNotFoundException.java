package com.example.SHA256Database;

class HashNotFoundException extends RuntimeException {
  public HashNotFoundException (String b64Hash) {
    super("Could not find hash " + b64Hash);
  }
}
