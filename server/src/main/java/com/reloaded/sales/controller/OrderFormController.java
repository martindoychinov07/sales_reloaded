package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.service.OrderFormService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "orderForm", description = "orderForm service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/order")
public class OrderFormController {
  private final OrderFormService orderFormService;
  private final ModelMapper modelMapper;

  public OrderFormController(OrderFormService orderFormService) {
    this.orderFormService = orderFormService;
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  @PostMapping(
    value = "/createOrderForm",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public OrderFormDto createOrder(
    @RequestBody OrderFormDto orderFormDto
  ) {
    return toDto(orderFormService.createOrder(toEntity(orderFormDto)));
  }

  @PutMapping(
    value = "/updateOrderForm",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto updateOrder(
    @RequestBody OrderFormDto orderFormDto
  ) {
    return toDto(orderFormService.updateOrder(toEntity(orderFormDto)));
  }

  @DeleteMapping(
    value = "/deleteOrderForm",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrder(
    @RequestBody Integer id
  ) {
    orderFormService.deleteOrder(id);
  }

  @GetMapping(
    value = "/{id}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderById(
    @PathVariable int id
  ) {
    return toDto(orderFormService.getOrderById(id));
  }

  @GetMapping(
    value = "/copy/{id}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderCopyById(
    @PathVariable int id,
    @RequestParam boolean content
  ) {
    return toDto(orderFormService.getOrderCopyById(id, content));
  }

  @GetMapping(
    value = "/last/{orderTypeId}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getLastOrderByOrderType(
    @PathVariable Integer orderTypeId
  ) {
    return toDto(orderFormService.getLastOrderByOrderType(orderTypeId));
  }

  private OrderForm toEntity(OrderFormDto dto) {
    OrderForm entity = modelMapper.map(dto, OrderForm.class);

    if (dto.getOrderSupplier() != null) {
      entity.setOrderSupplier(modelMapper.map(dto.getOrderSupplier(), Contact.class));
    }

    if (dto.getOrderCustomer() != null) {
      entity.setOrderCustomer(modelMapper.map(dto.getOrderCustomer(), Contact.class));
    }

    if (dto.getOrderType() != null) {
      entity.setOrderType(modelMapper.map(dto.getOrderType(), OrderType.class));
    }

    if (dto.getOrderEntries() != null) {
      entity.getOrderEntries().forEach(item -> item.setEntryOrder(entity));
    }

    return entity;
  }

  private OrderFormDto toDto(OrderForm entity) {
    return modelMapper.map(
      entity,
      OrderFormDto.class
    );
  }
}
