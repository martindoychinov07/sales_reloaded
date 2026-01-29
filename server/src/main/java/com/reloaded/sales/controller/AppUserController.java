/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.controller;

import com.reloaded.sales.dto.AppUserDto;
import com.reloaded.sales.dto.filter.AppUserFilter;
import com.reloaded.sales.model.AppUser;
import com.reloaded.sales.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "appUser", description = "user service")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController implements CrudController<AppUserDto, AppUserFilter, AppUser> {

  private final AppUserService appUserService;
  private final ModelMapper modelMapper;

  @Operation(operationId = "createAppUser")
  @Override
  public AppUserDto create(@RequestBody AppUserDto user) {
    return toDto(appUserService.createAppUser(user));
  }

  @Operation(operationId = "updateAppUser")
  @Override
  public AppUserDto update(@PathVariable int id, @RequestBody AppUserDto appUserDto) {
    AppUser appUser = appUserService.updateAppUser(appUserDto);
    appUser.setUserId(id);
    return toDto(appUser);
  }

  @Operation(operationId = "deleteAppUser")
  @Override
  public void delete(@PathVariable int id) {
    appUserService.deleteAppUser(id);
  }

  @Operation(operationId = "findAppUser")
  @Override
  public Page<AppUserDto> find(@ParameterObject @ModelAttribute AppUserFilter filter) {
    return appUserService.findAllAppUser(filter).map(this::toDto);
  }

  @Operation(operationId = "getAppUserById")
  @Override
  public AppUserDto getById(@PathVariable int id) {
    return toDto(appUserService.getUserById(id));
  }

  public AppUser toEntity(AppUserDto dto) {
    return modelMapper.map(dto, AppUser.class);
  }

  public AppUserDto toDto(AppUser entity) {
    return modelMapper.map(entity, AppUserDto.class);
  }

  @Operation(operationId = "changePassword")
  @PatchMapping("/pwd")
  public void changePassword(@RequestBody String password) {
    appUserService.changePassword(password);
  }
}

