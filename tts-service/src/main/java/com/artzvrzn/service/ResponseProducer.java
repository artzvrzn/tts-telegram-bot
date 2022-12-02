package com.artzvrzn.service;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

public interface ResponseProducer {

  void produce(SendDocument sendDocument);
}
