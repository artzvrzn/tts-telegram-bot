package com.artzvrzn.controller;


import com.artzvrzn.service.UpdateProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Log4j2
public class UpdateController {
  private final UpdateProducer updateProducer;
  private TelegramBot bot;

  void registerBot(TelegramBot bot) {
    this.bot = bot;
  }

  public void processUpdate(Update update) {
    if (update == null) {
      log.error("null update has been received");
      return;
    }
    if (update.hasMessage()) {
      handleMessage(update);
    }
  }

  public void sendResponse(SendMessage message) {
    bot.sendMessage(message);
  }

  public void sendResponse(SendDocument document) {
    bot.sendMessage(document);
  }

  private void handleMessage(Update update) {
    Message message = update.getMessage();
    if (message.hasText()) {
      updateProducer.produce(update);
    }
  }
}
