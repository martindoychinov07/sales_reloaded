package com.reloaded.sales.controller;

import com.reloaded.sales.dto.UserDto;
import com.reloaded.sales.exception.AlreadyReported;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import com.reloaded.sales.service.UserService;
import com.reloaded.sales.model.User;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    UserDto userInputDto = new UserDto(1, "test", "role", 1L);
    Long userId = 1L;


    @Test
    void testSignUpSuccess() throws Exception {
        userController.createUser(userInputDto);

        Mockito.verify(userService).signUp(userInput);
    }

    @Test
    void testSignUpFail() throws Exception {
        Mockito.doThrow(new AlreadyReported("User already exists"))
                .when(userService).signUp(Mockito.any(User.class));

        assertThrows(AlreadyReported.class, () -> {
            userController.createUser(userInput);
        });

        Mockito.verify(userService).signUp(userInput);
    }

    @Test
    void testGetUserById() {
        Optional<User> expected = Optional.of(userInput);

        Mockito.when(userService.getUserById(userId)).thenReturn(expected);

        Optional<User> result = userController.getUserById(userId.toString());

        assertTrue(result.isPresent());
        assertEquals(userInput, result.get());
        Mockito.verify(userService).getUserById(userId);
    }
}