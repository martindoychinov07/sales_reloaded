/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.TranslationFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.repository.TranslationRepository;
import com.reloaded.sales.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.anyLike;

@Service
@Transactional
@RequiredArgsConstructor
public class TranslationService {
  private final TranslationRepository translationRepository;

  public Translation createTranslation(Translation translation) {
    translation.setTranslationId(null);
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

    Specification<Translation> spec = (root, query, cb) -> cb.conjunction();

    spec = spec.and(anyLike(filter.getTranslationKey(), r -> r.get(Translation.Fields.translationKey)));
    spec = spec.and(anyLike(filter.getEn(), r -> r.get(Translation.Fields.en)));
    spec = spec.and(anyLike(filter.getBg(), r -> r.get(Translation.Fields.bg)));

    return translationRepository.findAll(spec, paging);
  }

  @Transactional(readOnly = true)
  public List<Translation> findAllTranslations() {
    return translationRepository.findAll();
  }

}
