package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.OrderFormService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Tag(name = "orderForm", description = "orderForm service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/order")
public class OrderFormController {
  private final OrderFormService orderFormService;
  private final ModelMapper modelMapper;

  OrderFormController(OrderFormService orderFormService) {
    this.orderFormService = orderFormService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
          value = "/createOrderForm",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public OrderFormDto createOrder(@RequestBody OrderFormDto orderFormDto) {
    return toDto(orderFormService.create(toEntity(orderFormDto)));
  }

  @PutMapping(
          value = "/updateOrderForm",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto updateOrder(@RequestBody OrderFormDto orderFormDto) {
    return toDto(orderFormService.update(toEntity(orderFormDto)));
  }

  @DeleteMapping(
          value = "/deleteOrderForm",
          consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrder(@RequestBody Integer id) {
    orderFormService.delete(id);
  }

  @GetMapping(
          value = "/{id}",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderById(@PathVariable int id) {
    return toDto(orderFormService.getById(id));
  }

  @GetMapping(
          value = "/getOrderForms",
          produces = "application/json"
  )
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderFormDto> getOrderForms(
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
        return orderFormService.getOrderForms(
                paging.toOptional().get()
        );
    }

  private OrderForm toEntity(OrderFormDto dto) {
    OrderForm entity = modelMapper.map(dto, OrderForm.class);

    if (dto.getOrderSupplier() != null) {
      entity.setOrderSupplier(modelMapper.map(dto.getOrderSupplier(), Contact.class));
    }

    if (dto.getOrderCustomer() != null) {
      entity.setOrderCustomer(modelMapper.map(dto.getOrderCustomer(), Contact.class));
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
