package com.example.SHA256Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

/**
 * Represents a row of the hash table in the database.
 */
@Entity
@Table(indexes = {
  @Index(columnList="Count DESC", unique=false)
})
public class Hash implements java.io.Serializable {
  @Id
  @Column(name="Digest", length=32)
  private byte[] digest;

  @Column(name="Input", length=1024)
  private byte[] input;

  @Column(name="Count")
  private int count;

  public Hash() {} // Probably need this so it's a bean

  public Hash(byte[] input) { // TODO Set size limit
    this.input = Arrays.copyOf(input, input.length);
    this.count = 0;

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

  public int getCount() {
    return count;
  }

  public void setCount(int newCount) {
    count = newCount;
  }
}
