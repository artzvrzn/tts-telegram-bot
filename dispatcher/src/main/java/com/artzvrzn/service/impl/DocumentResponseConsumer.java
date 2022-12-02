package com.artzvrzn.service.impl;

import static com.artzvrzn.domain.constants.RabbitQueues.DOC_RESPONSE_MESSAGE_UPDATE;

import com.artzvrzn.controller.UpdateController;
import com.artzvrzn.service.ResponseConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

@Service
@Log4j2
@RequiredArgsConstructor
public class DocumentResponseConsumer implements ResponseConsumer<SendDocument> {
  private final UpdateController updateController;

  @Override
  @RabbitListener(queues = DOC_RESPONSE_MESSAGE_UPDATE)
  public void consume(SendDocument response) {
    log.info("document received");
    updateController.sendResponse(response);
  }
}
