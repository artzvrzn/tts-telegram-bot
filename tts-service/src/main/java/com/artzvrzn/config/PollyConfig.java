package com.artzvrzn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;

@Configuration
public class PollyConfig {
  @Value("${aws.polly.access-key}")
  private String accessKey;
  @Value("${aws.polly.secret-key}")
  private String secret;
  @Value("${aws.polly.region}")
  private String region;

  @Bean
  public PollyClient pollyClient() {
    AwsCredentials pollyCredentials = AwsBasicCredentials.create(accessKey, secret);
    return PollyClient.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(pollyCredentials))
        .build();
  }
}
