package com.artzvrzn.controller;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
  @Value("${bot.username}")
  private String botUsername;
  @Value("${bot.token}")
  private String botToken;
  private final UpdateController updateController;

  @PostConstruct
  public void init() {
    updateController.registerBot(this);
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    updateController.processUpdate(update);
  }

  public void sendMessage(SendMessage message) {
    if (message != null) {
      try {
        execute(message);
      } catch (TelegramApiException e) {
        log.error(e);
      }
    }
  }

  public void sendMessage(SendDocument document) {
    if (document != null) {
      try {
        execute(document);
      } catch (TelegramApiException e) {
        log.error(e);
      }
    }
  }
}
