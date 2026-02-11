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

@Tag(name = "exchange", description = "exchange service") // Swagger documentation
@RestController // Marks this as REST controller
@RequestMapping("/api/exchange") // Base URL for exchange endpoints
@RequiredArgsConstructor // Generates constructor for final fields
public class ExchangeController implements CrudController<ExchangeDto, ExchangeFilter, Exchange> {

  private final ExchangeService exchangeService; // Business logic layer
  private final ModelMapper modelMapper; // Used for Entity <-> DTO mapping

  /**
   * Creates a new exchange record
   */
  @Operation(operationId = "createExchange")
  @Override
  public ExchangeDto create(@RequestBody ExchangeDto exchangeDto) {
    return toDto(exchangeService.createExchange(toEntity(exchangeDto)));
  }

  /**
   * Updates an existing exchange record
   */
  @Operation(operationId = "updateExchange")
  @Override
  public ExchangeDto update(@PathVariable int id, @RequestBody ExchangeDto exchangeDto) {
    Exchange exchange = toEntity(exchangeDto);
    exchange.setExchangeId(id); // Ensures ID comes from URL path
    return toDto(exchangeService.updateExchange(exchange));
  }

  /**
   * Deletes exchange record by ID
   */
  @Operation(operationId = "deleteExchange")
  @Override
  public void delete(@PathVariable int id) {
    exchangeService.deleteExchange(id);
  }

  /**
   * Returns paginated list of exchange records using filter criteria
   */
  @Operation(operationId = "findExchange")
  @Override
  public Page<ExchangeDto> find(@ParameterObject @ModelAttribute ExchangeFilter filter) {
    return exchangeService.findExchange(filter).map(this::toDto);
  }

  /**
   * Returns exchange record by ID
   */
  @Operation(operationId = "getExchangeById")
  @Override
  public ExchangeDto getById(int id) {
    return toDto(exchangeService.getExchangeById(id));
  }

  /**
   * Converts DTO to Entity
   */
  public Exchange toEntity(ExchangeDto dto) {
    return modelMapper.map(dto, Exchange.class);
  }

  /**
   * Converts Entity to DTO
   */
  public ExchangeDto toDto(Exchange entity) {
    return modelMapper.map(entity, ExchangeDto.class);
  }
}
