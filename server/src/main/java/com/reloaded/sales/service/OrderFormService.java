package com.reloaded.sales.service;

import com.reloaded.sales.dto.ContactDto;
import com.reloaded.sales.dto.ExchangeDto;
import com.reloaded.sales.dto.OrderFormDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.repository.OrderFormRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderFormService {
  final OrderFormRepository orderFormRepository;
  final ModelMapper modelMapper;

  OrderFormService(OrderFormRepository orderFormRepository) {
    this.orderFormRepository = orderFormRepository;
    this.modelMapper = new ModelMapper();
  }

  public OrderForm create(OrderForm orderForm) {
    return orderFormRepository.save(orderForm);
  }

  public OrderForm update(OrderForm changes) {
    OrderForm entity = orderFormRepository
      .findById(changes.getOrderId())
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

  public Page<OrderFormDto> getOrderForms(Pageable paging) {
    return orderFormRepository.findAll(paging)
      .map(o -> new OrderFormDto(
              o.getOrderId(),
              o.getOrderVersion(),
              o.getOrderRefId(),
              o.getOrderState(),
              o.getOrderDate(),
              o.getOrderBook(),
              o.getOrderNum(),
              o.getOrderType(),
              o.getOrderView(),
              o.getOrderUserId(),
              modelMapper.map(o.getOrderSupplier(), ContactDto.class),
              modelMapper.map(o.getOrderCustomer(), ContactDto.class),
              o.getOrderNote(),
              o.getOrderTagVce(),
              o.getOrderTagRcvd(),
              o.getOrderTagDlvd(),
              o.getOrderTagRef(),
              o.getOrderTagPaymentTerm(),
              o.getOrderAvailability(),
              o.getOrderRows(),
              o.getOrderVat(),
              o.getOrderCcy(),
              o.getOrderRate(),
              o.getOrderTotal(),
              o.getOrderTotalTax(),
              o.getOrderEntries()
      ));
  }

}
