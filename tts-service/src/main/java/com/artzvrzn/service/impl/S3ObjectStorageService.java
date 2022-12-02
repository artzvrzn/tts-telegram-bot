package com.artzvrzn.service.impl;

import com.artzvrzn.domain.Media;
import com.artzvrzn.service.ObjectStorageService;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@Log4j2
@RequiredArgsConstructor
public class S3ObjectStorageService implements ObjectStorageService {
  private final S3Client s3Client;
  private final S3Presigner s3Presigner;
  @Value("${aws.s3.bucket.tts.name}")
  private String bucket;
  @Value("${aws.s3.bucket.tts.expiration.unit}")
  private String timeUnit;
  @Value("${aws.s3.bucket.tts.expiration.value}")
  private long expirationTime;

  @Override
  public String upload(Media media) {
    log.info("uploading media to the storage...");
    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucket)
        .key(media.getName())
        .contentType(media.getContentType())
        .contentLength(media.getSize())
        .expires(generateExpirationTime())
        .build();
    RequestBody body = RequestBody.fromInputStream(media.getContent(), media.getSize());
    s3Client.putObject(request, body);
    log.info("media has been uploaded!");
    return getPresignUrlByKey(media.getName());
  }

  private String getPresignUrlByKey(String key) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(bucket)
        .key(key)
        .build();

    GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.of(expirationTime, ChronoUnit.valueOf(timeUnit)))
        .getObjectRequest(getObjectRequest)
        .build();

    URL url = s3Presigner.presignGetObject(getObjectPresignRequest).url();
    log.info("media url has been generated");
    return url.toString();
  }

  private Instant generateExpirationTime() {
    return Instant.now().plus(expirationTime, ChronoUnit.valueOf(timeUnit));
  }
}
