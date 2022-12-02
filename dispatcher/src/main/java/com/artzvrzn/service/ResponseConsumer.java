package com.artzvrzn.service;

public interface ResponseConsumer<R> {

  void consume(R response);
}
