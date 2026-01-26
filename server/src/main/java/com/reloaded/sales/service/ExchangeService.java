package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.ExchangeFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.repository.ExchangeRepository;
import com.reloaded.sales.util.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.orBlank;

@Service
@Transactional
public class ExchangeService {
  final ExchangeRepository exchangeRepository;

  ExchangeService(ExchangeRepository exchangeRepository) {
    this.exchangeRepository = exchangeRepository;
  }

  public Exchange createExchange(Exchange exchange) {
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

    Exchange probe = Exchange.builder()
      .exchangeBase(orBlank(filter.getExchangeBase()))
      .exchangeTarget(orBlank(filter.getExchangeTarget()))
      .build();

    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Exchange.Fields.exchangeBase, match -> match.contains().ignoreCase())
      .withMatcher(Exchange.Fields.exchangeTarget, match -> match.contains().ignoreCase());

    Example<Exchange> example = Example.of(probe, matcher);
    return exchangeRepository.findAll(example, paging);
  }
}
