package com.reloaded.sales.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.reloaded.sales.model.User;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {
    private static final String USER_NAME_1 = "test";
    private static final String USER_NAME_2 = "test2";
    private static final String USER_EMAIL = "test@email";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_ROLE = "simple";

    @Autowired
    private UserRepository userRepository;

    User userInput = new User(USER_NAME_1, USER_PASSWORD);

    @Test
    void testSaveAndFindByUsername() {
        userRepository.save(userInput);

        User found = userRepository.findByUsername(USER_NAME_1);

        assertNotNull(found);
        assertEquals(USER_NAME_1, found.getUsername());
    }

    @Test
    void testFindByUsernameNotFound() {
        User found = userRepository.findByUsername(USER_NAME_2);

        assertNull(found);
    }
}