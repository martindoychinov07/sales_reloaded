package com.example.sales.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import com.example.sales.exception.UserExistsException;
import com.example.sales.service.UserService;
import com.example.sales.model.User;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    User userInput = new User("test", "pass");
    Long userId = 1L;


    @Test
    void testSignUpSuccess() throws Exception {
        userController.createUser(userInput);

        Mockito.verify(userService).signup(userInput);
    }

    @Test
    void testSignUpFail() throws Exception {
        Mockito.doThrow(new UserExistsException("User already exists"))
                .when(userService).signup(Mockito.any(User.class));

        assertThrows(UserExistsException.class, () -> {
            userController.createUser(userInput);
        });

        Mockito.verify(userService).signup(userInput);
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