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

@Tag(name = "orderType", description = "orderType service") // Swagger documentation
@RestController // Marks this as REST controller
@RequestMapping("/api/orderType") // Base URL for order type endpoints
@RequiredArgsConstructor
public class OrderTypeController implements CrudController<OrderTypeDto, OrderTypeFilter, OrderType> {

  private final OrderTypeService orderTypeService; // Business logic layer
  private final ModelMapper modelMapper; // Used for Entity <-> DTO mapping

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
    orderType.setTypeId(id); // Ensures ID comes from path
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
