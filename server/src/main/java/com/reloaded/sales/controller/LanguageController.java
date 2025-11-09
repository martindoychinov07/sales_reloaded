package com.reloaded.sales.controller;

import com.reloaded.sales.dto.LanguageDto;
import com.reloaded.sales.model.Language;
import com.reloaded.sales.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "language", description = "language service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@Controller
@RequestMapping("/language")
public class LanguageController {
    private final LanguageService languageService;
    private final ModelMapper modelMapper;

    LanguageController(LanguageService languageService) {
        this.languageService = languageService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageDto createLanguage(@RequestBody LanguageDto languageDto) {
        return toDto(languageService.createLanguage(toEntity(languageDto)));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public LanguageDto updateLanguage(@RequestBody LanguageDto languageDto) {
        return toDto(languageService.updateLanguage(toEntity(languageDto)));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLanguage(String id) {
        languageService.deleteLanguage(id);
    }

    @GetMapping("/getAll")
    public List<LanguageDto> getLanguages() {
        return languageService.getLanguages();
    }

    private Language toEntity(LanguageDto dto) {
        return modelMapper.map(
                dto,
                Language.class
        );
    }

    private LanguageDto toDto(Language entity) {
        return modelMapper.map(
                entity,
                LanguageDto.class
        );
    }
}
