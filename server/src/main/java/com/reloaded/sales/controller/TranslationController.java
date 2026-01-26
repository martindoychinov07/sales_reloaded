package com.reloaded.sales.controller;

import com.reloaded.sales.dto.TranslationDto;
import com.reloaded.sales.dto.filter.TranslationFilter;
import com.reloaded.sales.model.Translation;
import com.reloaded.sales.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "translation", description = "translation service")
@RestController
@RequestMapping("/api/translation")
@RequiredArgsConstructor
public class TranslationController implements CrudController<TranslationDto, TranslationFilter, Translation> {

  private final TranslationService translationService;
  private final ModelMapper modelMapper;

  @Operation(operationId = "createTranslation")
  @Override
  public TranslationDto create(@RequestBody TranslationDto translationDto) {
    return toDto(translationService.createTranslation(toEntity(translationDto)));
  }

  @Operation(operationId = "updateTranslation")
  @Override
  public TranslationDto update(@PathVariable int id, @RequestBody TranslationDto translationDto) {
    Translation translation = toEntity(translationDto);
    translation.setTranslationId(id);
    return toDto(translationService.updateTranslation(translation));
  }

  @Operation(operationId = "deleteTranslation")
  @Override
  public void delete(@PathVariable int id) {
    translationService.deleteTranslation(id);
  }

  @Operation(operationId = "findTranslation")
  @Override
  public Page<TranslationDto> find(@ParameterObject @ModelAttribute TranslationFilter filter) {
    return translationService.findTranslation(filter).map(this::toDto);
  }

  @Operation(operationId = "getTranslationById")
  @Override
  public TranslationDto getById(@PathVariable int id) {
    return toDto(translationService.getTranslationById(id));
  }

  public Translation toEntity(TranslationDto dto) {
    return modelMapper.map(dto, Translation.class);
  }

  public TranslationDto toDto(Translation entity) {
    return modelMapper.map(entity, TranslationDto.class);
  }
}
