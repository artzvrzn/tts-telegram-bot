package com.artzvrzn.service.impl;

import com.artzvrzn.domain.Media;
import com.artzvrzn.service.AudioSynthesizer;
import com.artzvrzn.service.ObjectStorageService;
import com.artzvrzn.service.ResponseProducer;
import com.artzvrzn.service.TextToSpeechService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Log4j2
@RequiredArgsConstructor
public class TextToSpeechServiceImpl implements TextToSpeechService {
  private final AudioSynthesizer ttsProvider;
  private final ObjectStorageService objectStorage;
  private final ResponseProducer<SendDocument> responseProducer;

  @Async
  @Override
  public void process(Update update) {
    byte[] ttsResult = ttsProvider.synthesize(update.getMessage().getText());
    log.info("speech has been synthesized");

    try (InputStream mediaStream = new ByteArrayInputStream(ttsResult)) {
      Media media = Media.builder()
          .name(UUID.randomUUID().toString())
          .contentType("audio/mpeg")
          .size(ttsResult.length)
          .content(mediaStream)
          .build();

      String key = objectStorage.upload(media);
      responseProducer.produce(generateTelegramDocument(key, update.getMessage().getChatId()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private SendDocument generateTelegramDocument(String key, long chatId) {
    return SendDocument.builder()
        .chatId(chatId)
        .document(new InputFile(key))
        .build();
  }
}
