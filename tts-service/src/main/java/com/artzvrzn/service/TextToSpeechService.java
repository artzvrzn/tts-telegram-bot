package com.artzvrzn.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TextToSpeechService {

  void process(Update update);
}
