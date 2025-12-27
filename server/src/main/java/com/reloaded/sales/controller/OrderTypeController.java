package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderTypeDto;
import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.service.OrderTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "orderType", description = "orderType service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/orderType")
public class OrderTypeController {
  private final OrderTypeService orderTypeService;
  private final ModelMapper modelMapper;

  public OrderTypeController(OrderTypeService orderTypeService) {
    this.orderTypeService = orderTypeService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
    value = "/createOrderType",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public OrderTypeDto createOrderType(
    @RequestBody OrderTypeDto orderTypeDto
  ) {
    return toDto(orderTypeService.createOrderType(toEntity(orderTypeDto)));
  }

  @PutMapping(
    value = "/updateOrderType",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderTypeDto updateOrderType(
    @RequestBody OrderTypeDto orderTypeDto
  ) {
    return toDto(orderTypeService.updateOrderType(toEntity(orderTypeDto)));
  }

  @DeleteMapping(
    value = "/deleteOrderType",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrderType(
    @RequestBody Integer id
  ) {
    orderTypeService.deleteOrderType(id);
  }

  @GetMapping(
    value = "/findOrderType",
    produces = "application/json"
  )
  public Page<OrderTypeDto> findOrderType(
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

    return orderTypeService.findOrderType(
      paging
    ).map(this::toDto);
  }

  private OrderType toEntity(OrderTypeDto dto) {
    return modelMapper.map(
      dto,
      OrderType.class
    );
  }

  private OrderTypeDto toDto(OrderType entity) {
    return modelMapper.map(
      entity,
      OrderTypeDto.class
    );
  }
}
