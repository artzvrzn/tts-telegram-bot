package com.artzvrzn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TextToSpeechApplication {

  public static void main(String[] args) {
    SpringApplication.run(TextToSpeechApplication.class, args);
  }
}