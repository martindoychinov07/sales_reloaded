package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.model.OrderTypeState;
import com.reloaded.sales.repository.OrderTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderTypeService {
  private final OrderTypeRepository orderTypeRepository;

  public OrderTypeService(OrderTypeRepository orderTypeRepository) {
    this.orderTypeRepository = orderTypeRepository;
  }

  public OrderType createOrderType(OrderType orderType) {
    return orderTypeRepository.save(orderType);
  }

  public OrderType updateOrderType(OrderType changes) {
    OrderType entity = orderTypeRepository
      .findById(changes.getTypeId())
      .orElseThrow(() -> new NotFound("OrderType not found"));

    BeanUtils.copyProperties(changes, entity, OrderType.Fields.typeId);

    return orderTypeRepository.save(entity);
  }

  public void deleteOrderType(Integer id) {
    OrderType orderType = orderTypeRepository.findById(id)
      .orElseThrow(() -> new NotFound("OrderType not found"));
    orderType.setTypeState(OrderTypeState.deleted);
    orderTypeRepository.save(orderType);
  }

  public OrderType getOrderTypeById(Integer id) {
    return orderTypeRepository.findById(id).orElseThrow(() -> new NotFound("orderType"));
  }

  @Transactional(readOnly = true)
  public Page<OrderType> findOrderType(Pageable paging) {
    OrderType probe = OrderType.builder()
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues();

    Example<OrderType> example = Example.of(probe, matcher);
    return orderTypeRepository.findAll(example, paging);
  }
}
