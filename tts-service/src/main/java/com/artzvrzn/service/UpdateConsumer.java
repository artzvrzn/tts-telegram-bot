package com.artzvrzn.service;

import java.io.IOException;

public interface UpdateConsumer<T> {

  void consume(T message) throws IOException;
}
