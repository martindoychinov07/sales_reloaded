package com.reloaded.sales.controller;

import com.reloaded.sales.dto.TranslationDto;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.service.TranslationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "translation", description = "translation service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/translation")
public class TranslationController {
  private final TranslationService translationService;
  private final ModelMapper modelMapper;

  public TranslationController(TranslationService translationService) {
    this.translationService = translationService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
    value = "/createTranslation",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public TranslationDto createTranslation(TranslationDto translationDto) {
    return toDto(translationService.createTranslation(toEntity(translationDto)));
  }

  @PutMapping(
    value = "/updateTranslation",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public TranslationDto updateTranslation(TranslationDto translationDto) {
    return toDto(translationService.updateTranslation(toEntity(translationDto)));
  }

  @DeleteMapping(
    value = "/deleteTranslation",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteTranslation(Integer id) {
    translationService.deleteTranslation(id);
  }

  @GetMapping(
    value = "/findTranslation",
    produces = "application/json"
  )
  public Page<TranslationDto> findTranslation(
    @RequestParam Optional<String> key,
    @RequestParam Optional<String> en,
    @RequestParam Optional<String> bg,
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

    return translationService.findTranslationByKeyEnBg(
      key.orElse(null),
      en.orElse(null),
      bg.orElse(null),
      paging
    ).map(this::toDto);
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
