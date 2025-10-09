package com.example.sales.service;

import com.example.sales.exception.*;
import com.example.sales.model.User;
import com.example.sales.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSignUpSuccess() {
        User user = new User("test", "pass");
        userService.signup(user);

        User found = userRepository.findByUsername("test");
        assertNotNull(found);
        assertNotNull(found.getId());
    }

    @Test
    void testSignUpFailExists() throws UserExistsException {
        User user = new User("test", "pass");
        userService.signup(user);

        User duplicate = new User("test", "pass");
        assertThrows(UserExistsException.class, () -> {
            userService.signup(duplicate);
        });
    }

    @Test
    void testLoginSuccess() throws Exception {
        User signupUser = new User("test", "pass");
        userService.signup(signupUser);

        // mock request with fake IP
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        User loginUser = new User("test", "pass");
        userService.login(loginUser, request);

        userService.logout();

        User loginAgain = new User("test", "pass");
        userService.login(loginAgain, request);

        Object userId = session.getAttribute("userId");
        Object username = session.getAttribute("username");

        assertNotNull(userId);
        assertEquals("test", username);
    }

    @Test
    void testLogInAlreadyLoggedIn() throws Exception {
        User signupUser = new User("test", "pass");
        userService.signup(signupUser);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        User loginUser = new User("test", "pass");
        userService.login(loginUser, request);

        User loginAgain = new User("test", "pass");
        assertThrows(UserAlreadyLoggedInException.class, () -> {
            userService.login(loginAgain, request);
        });
    }

    @Test
    void testLogOutAlreadyLoggedOut() throws Exception {
        User user = new User("test", "pass");
        userService.signup(user);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        User loginUser = new User("test", "pass");
        userService.login(loginUser, request);

        userService.logout();

        assertThrows(UserAlreadyLoggedOutException.class, () -> {
            userService.logout();
        });
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        User signupUser = new User("test", "pass");
        userService.signup(signupUser);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        User loginUser = new User("test", "pass");
        userService.login(loginUser, request);

        ResponseEntity<String> response = userService.delete("test");

        assertEquals("User deleted successfully", response.getBody());
        assertTrue(userService.isLoggedOut()); // session should be invalidated

        assertNull(userRepository.findByUsername("test"));
    }

    @Test
    void testDeleteUserFailsWhenLoggedOut() throws Exception {
        User signupUser = new User("test", "pass");
        userService.signup(signupUser);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        User loginUser = new User("test", "pass");
        userService.login(loginUser, request);

        userService.logout();

        assertThrows(UserAlreadyLoggedOutException.class, () -> {
            userService.delete("test");
        });
    }
}
