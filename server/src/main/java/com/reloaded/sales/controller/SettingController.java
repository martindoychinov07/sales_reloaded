package com.reloaded.sales.controller;

import com.reloaded.sales.dto.SettingDto;
import com.reloaded.sales.dto.filter.SettingFilter;
import com.reloaded.sales.model.Setting;
import com.reloaded.sales.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing application settings.
 * Provides standard CRUD operations:
 *  - create
 *  - update
 *  - delete
 *  - find (paginated with filter)
 *  - get by ID
 */
@Tag(name = "setting", description = "setting service") // Swagger documentation
@RestController
@RequestMapping("/api/setting") // Base URL for setting endpoints
@RequiredArgsConstructor
public class SettingController implements CrudController<SettingDto, SettingFilter, Setting> {

  private final SettingService settingService; // Service layer for business logic
  private final ModelMapper modelMapper;       // Mapper for DTO <-> Entity

  /**
   * Creates a new setting
   */
  @Operation(operationId = "createSetting")
  @Override
  public SettingDto create(@RequestBody SettingDto settingDto) {
    return toDto(settingService.createSetting(toEntity(settingDto)));
  }

  /**
   * Updates an existing setting by ID
   */
  @Operation(operationId = "updateSetting")
  @Override
  public SettingDto update(@PathVariable int id, @RequestBody SettingDto settingDto) {
    Setting setting = toEntity(settingDto);
    setting.setSettingId(id); // Ensure ID comes from the path
    return toDto(settingService.updateSetting(setting));
  }

  /**
   * Deletes a setting by ID
   */
  @Operation(operationId = "deleteSetting")
  @Override
  public void delete(@PathVariable int id) {
    settingService.deleteSetting(id);
  }

  /**
   * Returns a paginated list of settings filtered by criteria
   */
  @Operation(operationId = "findSetting")
  @Override
  public Page<SettingDto> find(@ParameterObject @ModelAttribute SettingFilter filter) {
    return settingService.findSetting(filter).map(this::toDto);
  }

  /**
   * Returns a single setting by ID
   */
  @Operation(operationId = "getSettingById")
  @Override
  public SettingDto getById(@PathVariable int id) {
    return toDto(settingService.getSettingById(id));
  }

  /**
   * Converts Setting DTO to Entity
   */
  public Setting toEntity(SettingDto dto) {
    return modelMapper.map(dto, Setting.class);
  }

  /**
   * Converts Setting Entity to DTO
   */
  public SettingDto toDto(Setting entity) {
    return modelMapper.map(entity, SettingDto.class);
  }
}
