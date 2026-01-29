/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.OrderTypeFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.model.OrderTypeState;
import com.reloaded.sales.repository.OrderTypeRepository;
import com.reloaded.sales.util.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.orBlank;
import static com.reloaded.sales.util.ServiceUtils.orElse;

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

  @Transactional(readOnly = true)
  public OrderType getOrderTypeById(Integer id) {
    return orderTypeRepository.findById(id).orElseThrow(() -> new NotFound("orderType"));
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(OrderType.Fields.typeId)
  );

  @Transactional(readOnly = true)
  public Page<OrderType> findOrderType(OrderTypeFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    OrderType probe = OrderType.builder()
      .typeCounter(orElse(filter.getTypeCounter(), null))
      .typeEval(orElse(filter.getTypeEval(), null))
      .typeNote(orBlank(filter.getTypeNote()))
      .build();

    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(OrderType.Fields.typeCounter, match -> match.exact())
      .withMatcher(OrderType.Fields.typeEval, match -> match.exact())
      .withMatcher(OrderType.Fields.typeNote, match -> match.contains().ignoreCase());

    Example<OrderType> example = Example.of(probe, matcher);
    return orderTypeRepository.findAll(example, paging);
  }
}
