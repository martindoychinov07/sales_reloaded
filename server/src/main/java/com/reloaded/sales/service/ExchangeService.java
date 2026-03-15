/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.ExchangeFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.repository.ExchangeRepository;
import com.reloaded.sales.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.anyLike;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeService {

  private final ExchangeRepository exchangeRepository;

  public Exchange createExchange(Exchange exchange) {
    exchange.setExchangeId(null);
    return exchangeRepository.save(exchange);
  }

  public Exchange updateExchange(Exchange exchange) {
    Exchange exists = exchangeRepository.findById(exchange.getExchangeId())
      .orElseThrow(() -> new NotFound("Exchange not found"));

    BeanUtils.copyProperties(exchange, exists, "exchangeId");

    return exchangeRepository.save(exists);
  }

  public void deleteExchange(Integer id) {
    Exchange exists = exchangeRepository.findById(id)
      .orElseThrow(() -> new NotFound("Exchange not found"));

    exchangeRepository.delete(exists);
  }

  @Transactional(readOnly = true)
  public Exchange getExchangeById(Integer id) {
    return exchangeRepository.findById(id).orElseThrow(() -> new NotFound("exchange"));
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(Exchange.Fields.exchangeId)
  );

  @Transactional(readOnly = true)
  public Page<Exchange> findExchange(ExchangeFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    Specification<Exchange> spec = (root, query, cb) -> cb.conjunction();

    spec = spec.and(anyLike(filter.getExchangeTarget(), r -> r.get(Exchange.Fields.exchangeTarget)));
    spec = spec.and(anyLike(filter.getExchangeSource(), r -> r.get(Exchange.Fields.exchangeSource)));

    return exchangeRepository.findAll(spec, paging);
  }

}
