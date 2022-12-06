package com.artzvrzn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AWSConfig {
  @Value("${aws.s3.access-key}")
  private String s3AccessKey;
  @Value("${aws.s3.secret-key}")
  private String s3Secret;
  @Value("${aws.s3.region}")
  private String s3Region;

  @Value("${aws.polly.access-key}")
  private String pollyAccessKey;
  @Value("${aws.polly.secret-key}")
  private String pollySecret;
  @Value("${aws.polly.region}")
  private String pollyRegion;

  @Bean
  public S3Client s3Client() {
    AwsCredentials s3Credentials = AwsBasicCredentials.create(s3AccessKey, s3Secret);
    return S3Client.builder()
        .region(Region.of(s3Region))
        .credentialsProvider(StaticCredentialsProvider.create(s3Credentials))
        .build();
  }

  @Bean
  public S3Presigner s3Presigner() {
    AwsCredentials s3Credentials = AwsBasicCredentials.create(s3AccessKey, s3Secret);
    return S3Presigner.builder()
        .region(Region.of(s3Region))
        .credentialsProvider(StaticCredentialsProvider.create(s3Credentials))
        .build();
  }

  @Bean
  public PollyClient pollyClient() {
    AwsCredentials pollyCredentials = AwsBasicCredentials.create(s3AccessKey, s3Secret);
    return PollyClient.builder()
        .region(Region.of(s3Region))
        .credentialsProvider(StaticCredentialsProvider.create(pollyCredentials))
        .build();
  }
}
