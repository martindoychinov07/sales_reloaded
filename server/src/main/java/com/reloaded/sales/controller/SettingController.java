package com.reloaded.sales.controller;

import com.reloaded.sales.dto.SettingDto;
import com.reloaded.sales.model.Setting;
import com.reloaded.sales.service.SettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "setting", description = "setting service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/setting")
public class SettingController {
  private final SettingService settingService;
  private final ModelMapper modelMapper;

  public SettingController(SettingService settingService) {
    this.settingService = settingService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
    value = "/createSetting",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public SettingDto createSetting(SettingDto settingDto) {
    return toDto(settingService.createSetting(toEntity(settingDto)));
  }

  @PutMapping(
    value = "/updateSetting",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public SettingDto updateSetting(SettingDto settingDto) {
    return toDto(settingService.updateSetting(toEntity(settingDto)));
  }

  @DeleteMapping(
    value = "/deleteSetting",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteSetting(Integer id) {
    settingService.deleteSetting(id);
  }

  @GetMapping(
    value = "/findSetting",
    produces = "application/json"
  )
  public Page<SettingDto> findSetting(
    @RequestParam Optional<String> key,
    @RequestParam Optional<String> group,
    @RequestParam Optional<String> note,
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

    return settingService.findSettingByKeyGroupNote(
      key.orElse(null),
      group.orElse(null),
      note.orElse(null),
      paging
    ).map(this::toDto);
  }

  private Setting toEntity(SettingDto dto) {
    return modelMapper.map(
      dto,
      Setting.class
    );
  }

  private SettingDto toDto(Setting entity) {
    return modelMapper.map(
      entity,
      SettingDto.class
    );
  }
}
