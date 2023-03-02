package com.example.SHA256Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

/**
 * Represents a row of the hash table in the database.
 */
@Entity
public class Hash {
  @Id
  @Column(length=32)
  private byte[] digest;

  @Column(length=1024)
  private byte[] input;

  public Hash() {} // I think we need this for some reason

  public Hash(byte[] input) { // TODO Set size limit
    this.input = Arrays.copyOf(input, input.length);

    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      digest = md.digest(input);
    } catch (NoSuchAlgorithmException e) { // This should never happen.
      e.printStackTrace();
      System.exit(1);
    }
  }

  public byte[] getInput() {
    return input;
  }

  public byte[] getDigest() {
    return digest;
  }
}
