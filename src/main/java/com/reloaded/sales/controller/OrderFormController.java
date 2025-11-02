package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.OrderFormService;
import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/order")
public class OrderFormController {
    final OrderFormService orderFormService;
    private final ModelMapper modelMapper;

    OrderFormController(OrderFormService orderFormService, ModelMapper modelMapper) {
        this.orderFormService = orderFormService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderFormDto create(@RequestBody OrderFormDto orderFormDto) {
        return toDto(orderFormService.create(toEntity(orderFormDto)));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public OrderFormDto update(@RequestBody OrderFormDto orderFormDto) {
        return toDto(orderFormService.update(toEntity(orderFormDto)));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestBody Integer id) {
        orderFormService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderFormDto getById(@PathVariable int id) {
        return toDto(orderFormService.getById(id));
    }

//    @GetMapping("/findAll")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderForm> findAllById() {
//        return orderFormService.findAllById();
//    }

    private OrderForm toEntity(OrderFormDto dto) {
        return modelMapper.map(
                dto,
                OrderForm.class
        );
    }

    private OrderFormDto toDto(OrderForm entity) {
        return modelMapper.map(
                entity,
                OrderFormDto.class
        );
    }
}
