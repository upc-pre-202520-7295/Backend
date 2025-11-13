package com.betalyze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BetalyzeApplication {
  public static void main(String[] args) {
    SpringApplication.run(BetalyzeApplication.class, args);
  }
}
