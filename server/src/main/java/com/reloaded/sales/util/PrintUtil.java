/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.util;

import com.reloaded.sales.model.Translation;
import com.reloaded.sales.service.TranslationService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component("print")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrintUtil {

  private final TranslationService translationService;

  private Map<String, Map<String, String>> i18n;

  public PrintUtil(TranslationService translationService) {
    this.translationService = translationService;
  }

  public String t(String key, String lang) {
    if (i18n == null) {
      i18n = translationService.findAllTranslations().stream()
        .collect(Collectors.toMap(
          Translation::getTranslationKey,
          t -> {
            Map<String, String> m = new HashMap<>();
            m.put("en", t.getEn());
            m.put("bg", t.getBg());
            m.put("t1", t.getT1());
            m.put("t2", t.getT2());
            m.put("t3", t.getT3());
            return m;
          },
          (a, b) -> a   // protect against duplicate keys
        ));
    }

    return i18n.getOrDefault(key, Map.of()).getOrDefault(lang, key);
  }

  public BigDecimal rate(BigDecimal value, BigDecimal rate) {
    if (value != null && rate != null) {
      return value.multiply(rate);
    }
    else {
      return null;
    }
  }
}
