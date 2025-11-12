package com.reloaded.sales.controller;

import com.reloaded.sales.dto.UserDto;
import com.reloaded.sales.exception.AlreadyReported;
import com.reloaded.sales.model.User;
import com.reloaded.sales.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "user", description = "user service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserController(UserService userService) {
    this.userService = userService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto createUser(@RequestBody UserDto user) {
    return toDto(userService.signUp(toEntity(user)));
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public String logUser(@RequestBody User user, HttpServletRequest request) {
    return userService.login(user, request);
  }
//
//  @GetMapping("/logout")
//  @ResponseStatus(HttpStatus.OK)
//  public void logOutUser() throws AlreadyReported {
//    userService.logout();
//  }

  @DeleteMapping("/delete")
  public void deleteUser(@RequestBody String username) {
    userService.delete(username);
  }

  @PatchMapping("/pass")
  public void changePassword(@RequestBody String password) {
    userService.changePassword(password);
  }

  @PatchMapping("/name")
  @ResponseStatus(HttpStatus.OK)
  public void changeUsername(@RequestBody String username) {
    userService.changeUsername(username);
  }

//    @PostMapping("/type")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<String> getUserRole(@RequestBody String username) {
//        return userService.getUserRole(username);
//    }

  @PostMapping("/{id}")
  public Optional<User> getUserById(@RequestParam String idStr) {
    Long id = Long.parseLong(idStr.trim());
    return userService.getUserById(id);
  }

  @GetMapping("/findAll")
  public List<UserDto> findAllUsers() {
    return userService.findAllUsers();
  }

  private User toEntity(UserDto dto) {
    return modelMapper.map(
      dto,
      User.class
    );
  }

  private UserDto toDto(User entity) {
    return modelMapper.map(
      entity,
      UserDto.class
    );
  }
}

