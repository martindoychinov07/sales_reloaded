package com.reloaded.sales.service;

import com.reloaded.sales.dto.TranslationDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.repository.TranslationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {
    final TranslationRepository translationRepository;

    TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public Translation createTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public Translation updateTranslation(Translation translation) {
        Translation exists = translationRepository.findById(translation.getTranslationId())
                .orElseThrow(() -> new NotFound("Translation not found"));

        BeanUtils.copyProperties(translation, exists, "translationId");

        return translationRepository.save(exists);
    }

    public void deleteTranslation(Integer id) {
        Translation translation = translationRepository.findById(id)
                .orElseThrow(() -> new NotFound("Translation not found"));

        translationRepository.delete(translation);
    }

    public List<TranslationDto> getTranslations() {
        return translationRepository.findAll()
                .stream()
                .map(t -> new TranslationDto(
                        t.getTranslationId(),
                        t.getTranslationLang(),
                        t.getTranslationKey(),
                        t.getTranslationValue(),
                        t.getTranslationExpired()
                ))
                .collect(Collectors.toList());
    }
}
