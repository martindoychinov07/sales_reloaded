package com.reloaded.sales.controller;

import com.reloaded.sales.dto.TranslationDto;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.service.TranslationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "translation", description = "translation service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@Controller
@RequestMapping("/translation")
public class TranslationController {
    private final TranslationService translationService;
    private final ModelMapper modelMapper;

    TranslationController(TranslationService translationService) {
        this.translationService = translationService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TranslationDto createTranslation(TranslationDto translationDto) {
        return toDto(translationService.createTranslation(toEntity(translationDto)));
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public TranslationDto updateTranslation(TranslationDto translationDto) {
        return toDto(translationService.updateTranslation(toEntity(translationDto)));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTranslation(Integer id) {
        translationService.deleteTranslation(id);
    }

    @GetMapping("/getAll")
    public List<TranslationDto> getTranslations() {
        return translationService.getTranslations();
    }

    private Translation toEntity(TranslationDto dto) {
        return modelMapper.map(
                dto,
                Translation.class
        );
    }

    private TranslationDto toDto(Translation entity) {
        return modelMapper.map(
                entity,
                TranslationDto.class
        );
    }
}
