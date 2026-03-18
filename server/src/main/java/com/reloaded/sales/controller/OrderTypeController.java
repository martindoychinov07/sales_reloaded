/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderTypeDto;
import com.reloaded.sales.dto.filter.OrderTypeFilter;
import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.service.OrderTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "orderType", description = "orderType service")
@RestController
@RequestMapping("/api/orderType")
@RequiredArgsConstructor
public class OrderTypeController implements CrudController<OrderTypeDto, OrderTypeFilter, OrderType> {

  private final OrderTypeService orderTypeService;
  private final ModelMapper modelMapper;

  /**
   * Creates a new order type
   */
  @Operation(operationId = "createOrderType")
  @Override
  public OrderTypeDto create(@RequestBody OrderTypeDto orderTypeDto) {
    return toDto(orderTypeService.createOrderType(toEntity(orderTypeDto)));
  }

  /**
   * Updates an existing order type
   */
  @Operation(operationId = "updateOrderType")
  @Override
  public OrderTypeDto update(@PathVariable int id, @RequestBody OrderTypeDto orderTypeDto) {
    OrderType orderType = toEntity(orderTypeDto);
    orderType.setTypeId(id);
    return toDto(orderTypeService.updateOrderType(orderType));
  }

  /**
   * Deletes order type by ID
   */
  @Operation(operationId = "deleteOrderType")
  @Override
  public void delete(@PathVariable int id) {
    orderTypeService.deleteOrderType(id);
  }

  /**
   * Returns paginated list of order types using filter criteria
   */
  @Operation(operationId = "findOrderType")
  @Override
  public Page<OrderTypeDto> find(@ParameterObject @ModelAttribute OrderTypeFilter filter) {
    return orderTypeService.findOrderType(filter).map(this::toDto);
  }

  /**
   * Returns order type by ID
   */
  @Operation(operationId = "getOrderTypeById")
  @Override
  public OrderTypeDto getById(@PathVariable int id) {
    return toDto(orderTypeService.getOrderTypeById(id));
  }

  /**
   * Converts DTO to Entity
   */
  public OrderType toEntity(OrderTypeDto dto) {
    return modelMapper.map(dto, OrderType.class);
  }

  /**
   * Converts Entity to DTO
   */
  public OrderTypeDto toDto(OrderType entity) {
    return modelMapper.map(entity, OrderTypeDto.class);
  }
}
