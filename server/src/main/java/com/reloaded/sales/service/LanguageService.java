package com.reloaded.sales.service;

import com.reloaded.sales.dto.LanguageDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Language;
import com.reloaded.sales.repository.LanguageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {
    final LanguageRepository languageRepository;

    LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Language language) {
        Language exists = languageRepository.findById(language.getLangCode())
                .orElseThrow(() -> new NotFound("Language not found"));

        BeanUtils.copyProperties(language, exists, "langCode");

        return languageRepository.save(exists);
    }

    public void deleteLanguage(String id) {
        Language exists = languageRepository.findById(id)
                .orElseThrow(() -> new NotFound("Language not found"));

        languageRepository.delete(exists);
    }

    public Page<LanguageDto> getLanguages(Pageable paging) {
        return languageRepository.findAll(paging)
                .map(l -> new LanguageDto(
                        l.getLangCode(),
                        l.getLangOrder(),
                        l.getLangName(),
                        l.getLangCountry(),
                        l.getLangVariant()
                ));
    }
}
