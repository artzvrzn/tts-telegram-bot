package com.artzvrzn.domain;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Media {
  private String name;
  private String contentType;
  private long size;
  private InputStream content;
}
