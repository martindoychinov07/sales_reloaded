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

/**
 * REST controller for managing translations.
 * Provides standard CRUD operations:
 *  - create
 *  - update
 *  - delete
 *  - find (paginated with filter)
 *  - get by ID
 */
@Tag(name = "translation", description = "translation service") // Swagger documentation
@RestController
@RequestMapping("/api/translation") // Base URL for translation endpoints
@RequiredArgsConstructor
public class TranslationController implements CrudController<TranslationDto, TranslationFilter, Translation> {

  private final TranslationService translationService; // Service handling translation logic
  private final ModelMapper modelMapper;               // Mapper for DTO <-> Entity

  /**
   * Creates a new translation
   */
  @Operation(operationId = "createTranslation")
  @Override
  public TranslationDto create(@RequestBody TranslationDto translationDto) {
    return toDto(translationService.createTranslation(toEntity(translationDto)));
  }

  /**
   * Updates an existing translation by ID
   */
  @Operation(operationId = "updateTranslation")
  @Override
  public TranslationDto update(@PathVariable int id, @RequestBody TranslationDto translationDto) {
    Translation translation = toEntity(translationDto);
    translation.setTranslationId(id); // Ensure ID comes from the path
    return toDto(translationService.updateTranslation(translation));
  }

  /**
   * Deletes a translation by ID
   */
  @Operation(operationId = "deleteTranslation")
  @Override
  public void delete(@PathVariable int id) {
    translationService.deleteTranslation(id);
  }

  /**
   * Returns a paginated list of translations filtered by criteria
   */
  @Operation(operationId = "findTranslation")
  @Override
  public Page<TranslationDto> find(@ParameterObject @ModelAttribute TranslationFilter filter) {
    return translationService.findTranslation(filter).map(this::toDto);
  }

  /**
   * Returns a single translation by ID
   */
  @Operation(operationId = "getTranslationById")
  @Override
  public TranslationDto getById(@PathVariable int id) {
    return toDto(translationService.getTranslationById(id));
  }

  /**
   * Converts Translation DTO to Entity
   */
  public Translation toEntity(TranslationDto dto) {
    return modelMapper.map(dto, Translation.class);
  }

  /**
   * Converts Translation Entity to DTO
   */
  public TranslationDto toDto(Translation entity) {
    return modelMapper.map(entity, TranslationDto.class);
  }
}
