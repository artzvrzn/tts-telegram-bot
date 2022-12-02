package com.artzvrzn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {
  @Value("${aws.s3.access-key}")
  private String accessKey;
  @Value("${aws.s3.secret-key}")
  private String secret;
  @Value("${aws.s3.region}")
  private String region;

  @Bean
  public S3Client s3Client() {
    AwsCredentials s3Credentials = AwsBasicCredentials.create(accessKey, secret);
    return S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(s3Credentials))
        .build();
  }

  @Bean
  public S3Presigner s3Presigner() {
    AwsCredentials s3Credentials = AwsBasicCredentials.create(accessKey, secret);
    return S3Presigner.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(s3Credentials))
        .build();
  }
}
