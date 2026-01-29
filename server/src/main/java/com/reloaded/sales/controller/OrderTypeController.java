/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
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

  @Operation(operationId = "createOrderType")
  @Override
  public OrderTypeDto create(@RequestBody OrderTypeDto orderTypeDto) {
    return toDto(orderTypeService.createOrderType(toEntity(orderTypeDto)));
  }

  @Operation(operationId = "updateOrderType")
  @Override
  public OrderTypeDto update(@PathVariable int id, @RequestBody OrderTypeDto orderTypeDto) {
    OrderType orderType = toEntity(orderTypeDto);
    orderType.setTypeId(id);
    return toDto(orderTypeService.updateOrderType(orderType));
  }

  @Operation(operationId = "deleteOrderType")
  @Override
  public void delete(@PathVariable int id) {
    orderTypeService.deleteOrderType(id);
  }

  @Operation(operationId = "findOrderType")
  @Override
  public Page<OrderTypeDto> find(@ParameterObject @ModelAttribute OrderTypeFilter filter) {
    return orderTypeService.findOrderType(filter).map(this::toDto);
  }

  @Operation(operationId = "getOrderTypeById")
  @Override
  public OrderTypeDto getById(@PathVariable int id) {
    return toDto(orderTypeService.getOrderTypeById(id));
  }

  public OrderType toEntity(OrderTypeDto dto) {
    return modelMapper.map(dto, OrderType.class);
  }

  public OrderTypeDto toDto(OrderType entity) {
    return modelMapper.map(entity, OrderTypeDto.class);
  }
}
