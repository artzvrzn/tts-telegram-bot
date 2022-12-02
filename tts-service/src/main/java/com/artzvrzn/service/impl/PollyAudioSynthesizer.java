package com.artzvrzn.service.impl;

import com.artzvrzn.service.AudioSynthesizer;
import com.artzvrzn.service.LangDetector;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.Engine;
import software.amazon.awssdk.services.polly.model.LanguageCode;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.Voice;

@Service
@Log4j2
@RequiredArgsConstructor
public class PollyAudioSynthesizer implements AudioSynthesizer {
  private final LangDetector langDetector;
  private final PollyClient pollyClient;

  @Override
  public byte[] synthesize(String text) {
    Voice voice = defineVoice(text);

    SynthesizeSpeechRequest request = SynthesizeSpeechRequest.builder()
        .text(text)
        .engine(resolveEngine(voice))
        .voiceId(voice.id())
        .outputFormat(OutputFormat.MP3)
        .build();

    byte[] result = pollyClient.synthesizeSpeechAsBytes(request).asByteArray();
    log.info("polly tts request completed!");
    return result;
  }

  private Voice defineVoice(String text) {
    List<Voice> availableVoices = getVoices(text);
    List<Voice> preferableVoices = availableVoices
        .stream()
        .filter(v -> v.supportedEngines().contains(Engine.NEURAL))
        .collect(Collectors.toList());
    if (preferableVoices.isEmpty()) {
      return getRandomElement(availableVoices);
    } else {
      return getRandomElement(preferableVoices);
    }
  }

  private List<Voice> getVoices(String text) {
    List<LanguageCode> languageCodes = defineLanguageCodes(text);
    LanguageCode languageCode = getRandomElement(languageCodes);

    DescribeVoicesRequest voicesRequest = DescribeVoicesRequest.builder()
        .languageCode(languageCode)
        .build();

    DescribeVoicesResponse voicesResult = pollyClient.describeVoices(voicesRequest);
    return voicesResult.voices();
  }

  private List<LanguageCode> defineLanguageCodes(String text) {
    String language = langDetector.detect(text);
    List<LanguageCode> appropriateLanguageCodes = LanguageCode.knownValues()
        .stream()
        .filter(c -> c.toString().startsWith(language))
        .toList();
    if (!appropriateLanguageCodes.isEmpty()) {
      return appropriateLanguageCodes;
    }
    if ("ru".equals(language)) {
      return Collections.singletonList(LanguageCode.RU_RU);
    }
    return Collections.singletonList(LanguageCode.EN_US);
  }

  private Engine resolveEngine(Voice voice) {
    if (voice.supportedEngines().contains(Engine.NEURAL)) {
      return Engine.NEURAL;
    }
    return Engine.STANDARD;
  }

  private <T> T getRandomElement(List<? extends T> list) {
    int listSize = list.size();
    int randomElementIndex = ThreadLocalRandom.current().nextInt(listSize) % listSize;
    return list.get(randomElementIndex);
  }
}
