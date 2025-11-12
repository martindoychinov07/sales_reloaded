package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.repository.ExchangeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
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

  public Page<Exchange> findExchangeByBaseTarget(String base, String target, Pageable paging) {
    Exchange probe = Exchange.builder()
      .exchangeBase(base)
      .exchangeTarget(target)
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Exchange.Fields.exchangeBase, match.contains().ignoreCase())
      .withMatcher(Exchange.Fields.exchangeTarget, match.contains().ignoreCase());

    Example<Exchange> example = Example.of(probe, matcher);
    return exchangeRepository.findAll(example, paging);
  }
}
