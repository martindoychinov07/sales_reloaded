package com.reloaded.sales.controller;

import com.reloaded.sales.dto.PartnerDto;
import com.reloaded.sales.model.Partner;
import com.reloaded.sales.service.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/partner")
public class PartnerController {

  final PartnerService partnerService;
  private final ModelMapper modelMapper;

  public PartnerController(PartnerService partnerService) {
    this.partnerService = partnerService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping("/createPartner")
  @ResponseStatus(HttpStatus.CREATED)
  public PartnerDto createPartner(@RequestBody PartnerDto partnerDto) {
    return toDto(partnerService.create(toEntity(partnerDto)));
  }

  @PutMapping("/updatePartner")
  @ResponseStatus(HttpStatus.OK)
  public PartnerDto updatePartner(@RequestBody PartnerDto partnerDto) {
    return toDto(partnerService.update(toEntity(partnerDto)));
  }

  @DeleteMapping("/deletePartner")
  @ResponseStatus(HttpStatus.OK)
  public void deletePartner(@RequestBody int id) {
    partnerService.delete(id);
  }

  @GetMapping("/{id}")
  public PartnerDto getPartnerById(@PathVariable int id) {
    return toDto(partnerService.getById(id));
  }

  @GetMapping("/findAll")
  public Page<PartnerDto> findAll(
    @RequestParam String name,
    @RequestParam Optional<String> location,
    @RequestParam Optional<String> code,
    Pageable paging
  ) {
    return partnerService
      .findAllByNameLocationCode(
        name,
        location.orElse(null),
        code.orElse(null),
        paging
      ).map(this::toDto);
  }

  private Partner toEntity(PartnerDto dto) {
    return modelMapper.map(
      dto,
      Partner.class
    );
  }

  private PartnerDto toDto(Partner entity) {
    return modelMapper.map(
      entity,
      PartnerDto.class
    );
  }

}
