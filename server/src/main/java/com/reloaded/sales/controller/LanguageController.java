package com.reloaded.sales.controller;

import com.reloaded.sales.dto.LanguageDto;
import com.reloaded.sales.model.Language;
import com.reloaded.sales.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping(
            value = "/createLanguage",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageDto createLanguage(@RequestBody LanguageDto languageDto) {
        return toDto(languageService.createLanguage(toEntity(languageDto)));
    }

    @PutMapping(
            value = "/updateLanguage",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public LanguageDto updateLanguage(@RequestBody LanguageDto languageDto) {
        return toDto(languageService.updateLanguage(toEntity(languageDto)));
    }

    @DeleteMapping(
            value = "/deleteLanguage",
            consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteLanguage(String id) {
        languageService.deleteLanguage(id);
    }

    @GetMapping(
            value = "/getLanguages",
            produces = "application/json"
    )
    public Page<LanguageDto> getLanguages(
        @RequestParam Optional<Integer> page,
        @RequestParam Optional<Integer> size,
        @RequestParam Optional<String> sort,
        @RequestParam Optional<Sort.Direction> direction
    ) {
        PageRequest paging = PageRequest.ofSize(size.orElse(20));
        if (page.isPresent()) {
            paging = paging.withPage(page.get());
        }
        if (sort.isPresent()) {
            paging = paging.withSort(direction.orElse(Sort.Direction.ASC), sort.get());
        }
        return languageService.getLanguages(
                paging.toOptional().get()
        );
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
