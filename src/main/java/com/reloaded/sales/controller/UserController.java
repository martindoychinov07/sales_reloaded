package com.reloaded.sales.controller;

import java.util.List;
import java.util.Optional;

import com.reloaded.sales.DTO.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reloaded.sales.exception.UserAlreadyLoggedOutException;
import com.reloaded.sales.exception.UserExistsException;
import com.reloaded.sales.exception.UserNotFoundException;
import com.reloaded.sales.model.User;
import com.reloaded.sales.service.UserService;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public void createUser(@RequestBody User user) throws UserExistsException {
        userService.signUp(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logUser(@RequestBody User user, HttpServletRequest request) throws UserNotFoundException {
        return userService.login(user, request);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logOutUser() throws UserAlreadyLoggedOutException {
        return userService.logout();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody String username) throws
            UserAlreadyLoggedOutException, UserNotFoundException {
        return userService.delete(username);
    }

    @PatchMapping("/pass")
    public ResponseEntity<String> changePassword(@RequestBody String password) {
        return userService.changePassword(password);
    }

    @PatchMapping("/name")
    public ResponseEntity<String> changeUsername(@RequestBody String username) {
        return userService.changeUsername(username);
    }

    @PostMapping("/type")
    public Optional<String> getUserRole(@RequestBody String username) {
        return userService.getUserRole(username);
    }

    @PostMapping("/")
    public Optional<User> getUserById(@RequestBody String idStr) {
        Long id = Long.parseLong(idStr.trim());
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

//    @PatchMapping("/migrate")
//    public void migrate() {
//        userService.migratePasswords();
//    }
}

