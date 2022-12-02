package com.artzvrzn.service.impl;

import static com.artzvrzn.domain.constants.RabbitQueues.TTS_MESSAGE_UPDATE;

import com.artzvrzn.service.TextToSpeechService;
import com.artzvrzn.service.UpdateConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Log4j2
@RequiredArgsConstructor
public class UpdateConsumerImpl implements UpdateConsumer<Update> {
  private final TextToSpeechService ttsService;

  @Override
  @RabbitListener(queues = TTS_MESSAGE_UPDATE)
  public void consume(Update update) {
    log.info("update from chat {} received", update.getMessage().getChatId());
    ttsService.process(update);
  }
}
