package com.artzvrzn.config;

import static com.artzvrzn.domain.constants.RabbitQueues.DOC_RESPONSE_MESSAGE_UPDATE;
import static com.artzvrzn.domain.constants.RabbitQueues.TTS_MESSAGE_UPDATE;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue docMessageQueue() {
    return new Queue(TTS_MESSAGE_UPDATE);
  }

  @Bean
  public Queue docResponseMessageQueue() {
    return new Queue(DOC_RESPONSE_MESSAGE_UPDATE);
  }

}
