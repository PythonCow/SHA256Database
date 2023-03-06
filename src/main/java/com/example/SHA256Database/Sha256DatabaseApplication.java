package com.example.SHA256Database;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Sha256DatabaseApplication implements CommandLineRunner {

  @Autowired
  private HashRepository hashRepository;

  private String[] sampleInputs = new String[] {
    "Hello world!",
    "omega",
    "Ben Jamison",
    "ffff",
    "password123",
    "test",
    "sample",
    "SHA-256",
    "SHA 256",
    "Hexadecimal",
    "UTF-8",
    "[Object object]",
    "Base 64",
    "Java",
    "Spring Framework",
    "Javascript",
    "Typescript",
    "Angular",
    "Bootstrap",
    "HTML",
    "CSS",
    "JUnit",
    "Levi",
    "Neal",
    "Brigham",
    "Luke",
    "Emily", 
    "12345",
    "9876543210",
    "0123456789",
    "SHA256Database",
    "hashme",
    "donthashme",
    "Git",
    "Linux",
    "Vim",
    "VS Code",
    "Radagon",
    "Godrick",
    "Godfrey",
    "Marika",
    "Malenia",
    "Miquella",
    "Rykard",
    "Morgott"
  };

  public static void main(String[] args) {
    SpringApplication.run(Sha256DatabaseApplication.class, args);
  }

  public void run(String ... args) {
    Random rand = new Random();

    for (int i = 0; i < sampleInputs.length; i++) {
      Hash h = new Hash(sampleInputs[i].getBytes(StandardCharsets.UTF_8));

      h.setCount(rand.nextInt(10000));

      hashRepository.save(h);
    }

    System.out.println("Inserted sample hashes.");
  }
}
