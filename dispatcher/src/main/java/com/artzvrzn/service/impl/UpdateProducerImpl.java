package com.artzvrzn.service.impl;

import static com.artzvrzn.domain.constants.RabbitQueues.TTS_MESSAGE_UPDATE;

import com.artzvrzn.service.UpdateProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Log4j2
public class UpdateProducerImpl implements UpdateProducer {
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void produce(Update update) {
    log.info("text update received: " + update.getMessage().getText());
    rabbitTemplate.convertAndSend(TTS_MESSAGE_UPDATE, update);
  }
}
