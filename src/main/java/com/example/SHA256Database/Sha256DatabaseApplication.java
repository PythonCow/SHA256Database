package com.example.SHA256Database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Sha256DatabaseApplication implements CommandLineRunner {

  @Autowired
  HashRepository hashRepository;

  public static void main(String[] args) {
    SpringApplication.run(Sha256DatabaseApplication.class, args);
  }

  public void run(String ... args) {
    System.out.println("Command line runner is running...");
  }
}
