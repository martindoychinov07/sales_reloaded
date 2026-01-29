/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.TranslationFilter;
import com.reloaded.sales.exception.AlreadyExistsException;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.repository.TranslationRepository;
import com.reloaded.sales.util.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.orBlank;

@Service
@Transactional
public class TranslationService {
  private final TranslationRepository translationRepository;

  public TranslationService(TranslationRepository translationRepository) {
    this.translationRepository = translationRepository;
  }

  public Translation createTranslation(Translation translation) {
    if (translationRepository.existsTranslationByTranslationKey(translation.getTranslationKey())) {
      throw new AlreadyExistsException("Translation with this key already exists");
    }
    return translationRepository.save(translation);
  }

  public Translation updateTranslation(Translation changes) {
    Translation entity = translationRepository
      .findById(changes.getTranslationId())
      .orElseThrow(() -> new NotFound("Translation not found"));

    BeanUtils.copyProperties(changes, entity,Translation.Fields.translationId);

    return translationRepository.save(entity);
  }

  public void deleteTranslation(Integer id) {
    Translation translation = translationRepository.findById(id)
      .orElseThrow(() -> new NotFound("Translation not found"));

    translationRepository.delete(translation);
  }

  @Transactional(readOnly = true)
  public Translation getTranslationById(Integer id) {
    return translationRepository.findById(id).orElseThrow(() -> new NotFound("translation"));
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(Translation.Fields.translationKey)
  );

  @Transactional(readOnly = true)
  public Page<Translation> findTranslation(TranslationFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    Translation probe = Translation.builder()
      .translationKey(orBlank(filter.getTranslationKey()))
      .en(orBlank(filter.getEn()))
      .bg(orBlank(filter.getBg()))
      .build();

    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Translation.Fields.translationKey , match -> match.contains().ignoreCase())
      .withMatcher(Translation.Fields.en, match -> match.contains().ignoreCase())
      .withMatcher(Translation.Fields.bg, match -> match.contains().ignoreCase());

    Example<Translation> example = Example.of(probe, matcher);
    return translationRepository.findAll(example, paging);
  }

  @Transactional(readOnly = true)
  public List<Translation> findAllTranslations() {
    return translationRepository.findAll();
  }
}
