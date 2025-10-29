package com.reloaded.sales.service;

import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.repository.OrderFormRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderFormService {
    final OrderFormRepository orderFormRepository;

    OrderFormService(OrderFormRepository orderFormRepository) {
        this.orderFormRepository = orderFormRepository;
    }

    public OrderForm create(OrderForm orderForm) {
        return orderFormRepository.save(orderForm);
    }

    public OrderForm update(OrderForm changes) {
        OrderForm entity = orderFormRepository
                .findById(changes.getId())
                .orElseThrow(() -> new NotFound("Order form not found"));

        BeanUtils.copyProperties(changes, entity);

        return orderFormRepository.save(entity);
    }

    public void delete(Integer id) {
        OrderForm orderForm = orderFormRepository
                .findById(id)
                .orElseThrow(() -> new NotFound("Order form not found"));

        orderFormRepository.delete(orderForm);
    }

    public OrderForm getById(Integer id) {
        return orderFormRepository.findById(id).orElseThrow(() -> new NotFound("Order form not found"));
    }
}
