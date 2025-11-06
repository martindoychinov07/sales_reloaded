package com.reloaded.sales.service;

import com.reloaded.sales.dto.ExchangeDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.repository.ExchangeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ExchangeDto> getExchanges() {
        return exchangeRepository.findAll()
                .stream()
                .map(e -> new ExchangeDto(
                        e.getExchangeId(),
                        e.getExchangeDate(),
                        e.getExchangeBase(),
                        e.getExchangeTarget(),
                        e.getExchangeRate()
                ))
                .collect(Collectors.toList());
    }
}
