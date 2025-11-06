package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ExchangeDto;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.service.ExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "exchange", description = "exchange service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    private final ExchangeService exchangeService;
    private final ModelMapper modelMapper;

    ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeDto createExchange(@RequestBody ExchangeDto exchangeDto) {
        return toDto(exchangeService.createExchange(toEntity(exchangeDto)));
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeDto updateExchange(@RequestBody ExchangeDto exchangeDto) {
        return toDto(exchangeService.updateExchange(toEntity(exchangeDto)));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExchange(Integer id) {
        exchangeService.deleteExchange(id);
    }

    @GetMapping("/getAll")
    public List<ExchangeDto> getExchanges() {
        return exchangeService.getExchanges();
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