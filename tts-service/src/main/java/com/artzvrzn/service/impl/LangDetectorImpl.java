package com.artzvrzn.service.impl;

import com.artzvrzn.service.LangDetector;
import java.lang.Character.UnicodeBlock;
import java.util.Optional;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.stereotype.Service;

@Service
public class LangDetectorImpl implements LangDetector {

  @Override
  public String detect(String text) {
    if (isCyrillic(text)) {
      return "ru";
    }
    LanguageDetector detector = new OptimaizeLangDetector().loadModels();
    LanguageResult result = detector.detect(text);
    return result.getLanguage();
  }

  private static boolean isCyrillic(String text) {
    Optional<UnicodeBlock> any = text.chars()
        .mapToObj(UnicodeBlock::of)
        .filter(UnicodeBlock.CYRILLIC::equals)
        .findAny();
    return any.isPresent();
  }
}
