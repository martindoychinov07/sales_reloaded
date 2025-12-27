package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ExchangeDto;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.service.ExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "exchange", description = "exchange service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {
  private final ExchangeService exchangeService;
  private final ModelMapper modelMapper;

  public ExchangeController(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
    value = "/createExchange",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ExchangeDto createExchange(
    @RequestBody ExchangeDto exchangeDto
  ) {
    return toDto(exchangeService.createExchange(toEntity(exchangeDto)));
  }

  @PutMapping(
    value = "/updateExchange",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ExchangeDto updateExchange(
    @RequestBody ExchangeDto exchangeDto
  ) {
    return toDto(exchangeService.updateExchange(toEntity(exchangeDto)));
  }

  @DeleteMapping(
    value = "/deleteExchange",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteExchange(
    @RequestBody Integer id
  ) {
    exchangeService.deleteExchange(id);
  }

  @GetMapping(
    value = "/findExchange",
    produces = "application/json"
  )
  public Page<ExchangeDto> findExchange(
    @RequestParam Optional<String> exchangeBase,
    @RequestParam Optional<String> exchangeTarget,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<String> sort,
    @RequestParam Optional<Sort.Direction> direction
  ) {
    PageRequest paging = PageRequest.ofSize(size.orElse(20));
    if (page.isPresent()) {
      paging = paging.withPage(page.get());
    }
    if (sort.isPresent()) {
      paging = paging.withSort(direction.orElse(Sort.Direction.ASC), sort.get());
    }
    return exchangeService
      .findExchangeByBaseTarget(
        exchangeBase.orElse(null),
        exchangeTarget.orElse(null),
        paging
      ).map(this::toDto);
  }

  private Exchange toEntity(ExchangeDto dto) {
    return modelMapper.map(
      dto,
      Exchange.class
    );
  }

  private ExchangeDto toDto(Exchange entity) {
    return modelMapper.map(
      entity,
      ExchangeDto.class
    );
  }
}