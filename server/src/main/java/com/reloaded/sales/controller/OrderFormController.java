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

import java.util.List;
import java.util.Optional;

@Tag(name = "orderForm", description = "orderForm service") // Swagger documentation
@RestController // Marks this as REST controller
@RequestMapping("/api/order") // Base URL for order endpoints
@RequiredArgsConstructor
public class OrderFormController {

  private final OrderFormService orderFormService; // Business logic layer
  private final ModelMapper modelMapper; // Used for Entity <-> DTO mapping

  /**
   * Creates a new order
   */
  @PostMapping(
          value = "/",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public OrderFormDto createOrder(@RequestBody OrderFormDto orderFormDto) {
    return toDto(orderFormService.createOrder(toEntity(orderFormDto)));
  }

  /**
   * Updates an existing order
   */
  @PutMapping(
          value = "/{id:\\d+}",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto updateOrder(@PathVariable int id, @RequestBody OrderFormDto orderFormDto) {
    OrderForm orderForm = toEntity(orderFormDto);
    orderForm.setOrderId(id); // Ensures ID comes from path
    return toDto(orderFormService.updateOrder(orderForm));
  }

  /**
   * Deletes order by ID
   */
  @DeleteMapping(value = "/{id:\\d+}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrder(@PathVariable int id) {
    orderFormService.deleteOrder(id);
  }

  /**
   * Returns order by ID
   */
  @GetMapping(
          value = "/{id:\\d+}",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderById(@PathVariable int id) {
    return toDto(orderFormService.getOrderById(id));
  }

  /**
   * Creates a copy of one or more orders.
   * ids     - list of order IDs
   * content - whether to copy order entries
   */
  @GetMapping(
          value = "/copy",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderCopyByIds(
          @RequestParam List<Integer> ids,
          @RequestParam boolean content
  ) {
    return toDto(orderFormService.getOrderCopyById(ids, content));
  }

  /**
   * Returns the last order by order type.
   * Optional orderId can be used to exclude a specific order.
   */
  @GetMapping(
          value = "/last/{orderTypeId}",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public Optional<OrderFormDto> getLastOrderByOrderType(
          @PathVariable Integer orderTypeId,
          @RequestParam(required = false) Integer orderId
  ) {
    Optional<OrderForm> found =
            orderFormService.getLastOrderByOrderType(orderTypeId, orderId);

    return found
            .map(orderForm -> Optional.of(toDto(orderForm)))
            .orElse(null);
  }

  /**
   * Converts DTO to Entity.
   * Handles nested objects and relationships manually.
   */
  private OrderForm toEntity(OrderFormDto dto) {
    OrderForm entity = modelMapper.map(dto, OrderForm.class);

    // Map supplier
    if (dto.getOrderSupplier() != null) {
      entity.setOrderSupplier(
              modelMapper.map(dto.getOrderSupplier(), Contact.class)
      );
    }

    // Map customer
    if (dto.getOrderCustomer() != null) {
      entity.setOrderCustomer(
              modelMapper.map(dto.getOrderCustomer(), Contact.class)
      );
    }

    // Map order type
    if (dto.getOrderType() != null) {
      entity.setOrderType(
              modelMapper.map(dto.getOrderType(), OrderType.class)
      );
    }

    // Set back-reference for order entries
    if (dto.getOrderEntries() != null) {
      entity.getOrderEntries()
              .forEach(item -> item.setEntryOrder(entity));
    }

    return entity;
  }

  /**
   * Converts Entity to DTO
   */
  private OrderFormDto toDto(OrderForm entity) {
    return modelMapper.map(entity, OrderFormDto.class);
  }
}
