package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ExchangeDto;
import com.reloaded.sales.dto.filter.ExchangeFilter;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "exchange", description = "exchange service")
@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController implements CrudController<ExchangeDto, ExchangeFilter, Exchange> {

  private final ExchangeService exchangeService;
  private final ModelMapper modelMapper;

  @Operation(operationId = "createExchange")
  @Override
  public ExchangeDto create(@RequestBody ExchangeDto exchangeDto) {
    return toDto(exchangeService.createExchange(toEntity(exchangeDto)));
  }

  @Operation(operationId = "updateExchange")
  @Override
  public ExchangeDto update(@PathVariable int id, @RequestBody ExchangeDto exchangeDto) {
    Exchange exchange = toEntity(exchangeDto);
    exchange.setExchangeId(id);
    return toDto(exchangeService.updateExchange(exchange));
  }

  @Operation(operationId = "deleteExchange")
  @Override
  public void delete(@PathVariable int id) {
    exchangeService.deleteExchange(id);
  }

  @Operation(operationId = "findExchange")
  @Override
  public Page<ExchangeDto> find(@ParameterObject @ModelAttribute ExchangeFilter filter) {
    return exchangeService.findExchange(filter).map(this::toDto);
  }

  @Operation(operationId = "getExchangeById")
  @Override
  public ExchangeDto getById(int id) {
    return toDto(exchangeService.getExchangeById(id));
  }

  public Exchange toEntity(ExchangeDto dto) {
    return modelMapper.map(dto, Exchange.class);
  }

  public ExchangeDto toDto(Exchange entity) {
    return modelMapper.map(entity, ExchangeDto.class);
  }
}