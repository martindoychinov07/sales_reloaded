package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.OrderFormService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderFormDto createOrder(@RequestBody OrderFormDto orderFormDto) {
    return toDto(orderFormService.create(toEntity(orderFormDto)));
  }

  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto updateOrder(@RequestBody OrderFormDto orderFormDto) {
    return toDto(orderFormService.update(toEntity(orderFormDto)));
  }

  @DeleteMapping("/delete")
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrder(@RequestBody Integer id) {
    orderFormService.delete(id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public OrderFormDto getOrderById(@PathVariable int id) {
    return toDto(orderFormService.getById(id));
  }

//    @GetMapping("/findAll")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderForm> findAllById() {
//        return orderFormService.findAllById();
//    }

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
