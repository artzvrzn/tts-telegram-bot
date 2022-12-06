package com.artzvrzn.service;

public interface ResponseProducer<T> {

  void produce(T response);
}
