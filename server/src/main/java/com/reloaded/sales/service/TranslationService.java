package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.repository.TranslationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
  private final TranslationRepository translationRepository;

  public TranslationService(TranslationRepository translationRepository) {
    this.translationRepository = translationRepository;
  }

  public Translation createTranslation(Translation translation) {
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

  public Translation getTranslationById(Integer id) {
    return translationRepository.findById(id).orElseThrow(() -> new NotFound("translation"));
  }

  public Page<Translation> findTranslationByKeyEnBg(String key, String en, String bg, Pageable paging) {
    Translation probe = Translation.builder()
      .translationKey(key)
      .toEn(en)
      .toBg(bg)
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Translation.Fields.translationKey , match.exact().ignoreCase())
      .withMatcher(Translation.Fields.toEn, match.contains().ignoreCase())
      .withMatcher(Translation.Fields.toBg, match.contains().ignoreCase());

    Example<Translation> example = Example.of(probe, matcher);
    return translationRepository.findAll(example, paging);
  }
}
