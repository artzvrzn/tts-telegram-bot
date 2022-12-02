package com.artzvrzn.service.impl;

import static com.artzvrzn.domain.constants.RabbitQueues.DOC_RESPONSE_MESSAGE_UPDATE;

import com.artzvrzn.service.ResponseProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

@Service
@Log4j2
@RequiredArgsConstructor
public class ResponseProducerImpl implements ResponseProducer {
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void produce(SendDocument sendDocument) {
    rabbitTemplate.convertAndSend(DOC_RESPONSE_MESSAGE_UPDATE, sendDocument);
    log.info("tts response message has been sent");
  }
}
