package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.service.OrderFormService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "orderForm", description = "orderForm service")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderFormController {

  private final OrderFormService orderFormService;
  private final ModelMapper modelMapper;

  @PostMapping(
    value = "/",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public OrderFormDto createOrder(@RequestBody OrderFormDto orderFormDto) {
    return toDto(orderFormService.createOrder(toEntity(orderFormDto)));
  }

  @PutMapping(
    value = "/{id:\\d+}",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto updateOrder(@PathVariable int id, @RequestBody OrderFormDto orderFormDto) {
    OrderForm orderForm = toEntity(orderFormDto);
    orderForm.setOrderId(id);
    return toDto(orderFormService.updateOrder(orderForm));
  }

  @DeleteMapping(
    value = "/{id:\\d+}",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrder(@PathVariable int id) {
    orderFormService.deleteOrder(id);
  }

  @GetMapping(
    value = "/{id:\\d+}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderById(@PathVariable int id) {
    return toDto(orderFormService.getOrderById(id));
  }

  @GetMapping(
    value = "/copy/{id:\\d+}",
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
  public Optional<OrderFormDto> getLastOrderByOrderType(@PathVariable Integer orderTypeId) {
    Optional<OrderForm> found = orderFormService.getLastOrderByOrderType(orderTypeId);
    return found.map(orderForm -> Optional.of(toDto(orderForm))).orElse(null);
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
    return modelMapper.map(entity, OrderFormDto.class);
  }
}
