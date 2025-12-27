package com.reloaded.sales.controller;

import com.reloaded.sales.dto.AppUserDto;
import com.reloaded.sales.model.AppUser;
import com.reloaded.sales.service.AppUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Tag(name = "appUser", description = "user service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
public class AppUserController {

  private final AppUserService appUserService;
  private final ModelMapper modelMapper;

  public AppUserController(AppUserService appUserService) {
    this.appUserService = appUserService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
    value = "/createAppUser",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public AppUserDto createAppUser(@RequestBody AppUserDto user) {
    return toDto(appUserService.createAppUser(user));
  }

  @PutMapping(
    value = "/updateAppUser",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public AppUserDto updateAppUser(
    @RequestBody AppUserDto appUserDto
  ) {
    return toDto(appUserService.updateAppUser(appUserDto));
  }

  @DeleteMapping("/deleteAppUser")
  public void deleteAppUser(@RequestBody int id) {
    appUserService.deleteAppUser(id);
  }

  @PatchMapping("/changePassword")
  public void changePassword(@RequestBody String password) {
    appUserService.changePassword(password);
  }

  @GetMapping(
    value="/findAppUser",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public Page<AppUserDto> findAppUser(
    @RequestParam Optional<String> username,
    @RequestParam Optional<String> userRole,
    @RequestParam Optional<String> fullname,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<String> sort,
    @RequestParam Optional<Sort.Direction> direction
  ) {
    PageRequest paging = PageRequest.of(
      page.orElse(0),
      size.orElse(20),
      sort.isPresent()
        ? Sort.by(
        Sort.by(direction.orElse(Sort.Direction.ASC), sort.get()).getOrderFor(sort.get()),
        Sort.Order.asc(AppUser.Fields.userId)
      )
        : Sort.by(Sort.Order.asc(AppUser.Fields.userId)
      )
    );

    return appUserService
      .findAllAppUserByUsernameUserRoleFullname(
        username.orElse(null),
        userRole.orElse(null),
        fullname.orElse(null),
        paging
      ).map(this::toDto);
  }

  @GetMapping(
    value = "/{id}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public AppUserDto getUserById(
    @RequestParam Integer id
  ) {
    return toDto(appUserService.getUserById(id));
  }

  private AppUser toEntity(AppUserDto dto) {
    return modelMapper.map(
      dto,
      AppUser.class
    );
  }

  private AppUserDto toDto(AppUser entity) {
    return modelMapper.map(
      entity,
      AppUserDto.class
    );
  }
}

