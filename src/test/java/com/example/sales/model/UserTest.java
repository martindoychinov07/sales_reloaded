package com.example.sales.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static final String USER_NAME = "Petur";
    private static final String USER_PASSWORD = "pass123";

    @Test
    void testUser() {
        User user = new User(USER_NAME, USER_PASSWORD);
        assertEquals(USER_NAME, user.getUsername());
        assertEquals(USER_PASSWORD, user.getPassword());
    }
}