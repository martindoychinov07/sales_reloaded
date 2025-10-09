package com.example.sales.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sales.exception.*;
import com.example.sales.model.User;
import com.example.sales.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpSession session;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger loginLogger = LoggerFactory.getLogger(UserService.class);

    private static int logInCount = 0;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<String> signUp(User u) throws UserExistsException {
        if (isLoggedIn()) {
            throw new UserAlreadyLoggedInException("User already logged in");
        }

        if (userRepository.findByUsername(u.getUsername()) != null) {
            throw new UserExistsException("User already exists");
        }

        u.setPassword(passwordEncoder.encode(u.getPassword()));

        createUser(u);
        return ResponseEntity.ok("Sign Up successful");
    }

    public ResponseEntity<String> login(User u, HttpServletRequest request)
            throws UserNotFoundException, UserAlreadyLoggedInException, IncorrectPasswordException {


        if (isLoggedIn()) {
            throw new UserAlreadyLoggedInException("User already logged in");
        }

        User dbUser = userRepository.findByUsername(u.getUsername());
        if (dbUser == null) {
            throw new UserNotFoundException("No user with that username found");
        }

        boolean check = passwordEncoder.matches(u.getPassword(), dbUser.getPassword());
        String clientIp = request.getRemoteAddr();

        if (!check) {
            loginLogger.warn("LOGIN FAILED: user={}, ip={}, time={}",
                    u.getUsername(), clientIp, LocalDateTime.now());
            logInCount++;

            if (logInCount < 3) {
                throw new IncorrectPasswordException("Incorrect password");
            }
            else {
                throw new IncorrectPasswordException("Why do you persist?");
            }
        } else {
            session.setAttribute("userId", dbUser.getId());
            session.setAttribute("username", dbUser.getUsername());
            session.setMaxInactiveInterval(48 * 3600);

            loginLogger.info("LOGIN SUCCESS: user={}, ip={}, time={}",
                    dbUser.getUsername(), clientIp, LocalDateTime.now());

            logInCount = 0;

            return ResponseEntity.ok("Login successful");
        }
    }

    public ResponseEntity<String> logout() throws UserAlreadyLoggedOutException {
        if (!isLoggedIn()) {
            throw new UserAlreadyLoggedOutException("User already logged out");
        }

        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    @Transactional
    public ResponseEntity<String> delete(String username) throws
            UserAlreadyLoggedOutException, UserNotFoundException {

        if (isLoggedOut()) {
            throw new UserAlreadyLoggedOutException("User already logged out");
        }

        User dbUser = userRepository.findByUsername(username);
        if (dbUser == null) {
            throw new UserNotFoundException("No user with that username found");
        }

        userRepository.delete(dbUser);

        session.invalidate();

        return ResponseEntity.ok("User deleted successfully");
    }

    public boolean isLoggedIn() {
        return session.getAttribute("userId") != null;
    }

    public boolean isLoggedOut() {
        return session.getAttribute("userId") == null;
    }

    public Optional<User> getCurrentUser() {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return Optional.empty();
        }

        return userRepository.findById(userId);
    }

    public ResponseEntity<String> changePassword(String newPassword) throws UserAlreadyLoggedOutException {
        String username = Optional.of((String) session.getAttribute("username")).orElseThrow(() ->
                new UserAlreadyLoggedOutException("User logged out")
        );
        Long userId = Optional.of((Long) session.getAttribute("userId")).orElseThrow(() ->
                new UserAlreadyLoggedOutException("User logged out")
        );

        User user = userRepository.findByUsername(String.valueOf(username));

        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Successfully changed password");
    }

    public ResponseEntity<String> changeUsername(String newUsername) throws UserAlreadyLoggedOutException {
        String username = Optional.of((String) session.getAttribute("username")).orElseThrow(() ->
                new UserAlreadyLoggedOutException("User logged out")
        );

        Long userId = Optional.of((Long) session.getAttribute("userId")).orElseThrow(() ->
                new UserAlreadyLoggedOutException("User logged out")
        );

        User oldUser = userRepository.findByUsername(String.valueOf(newUsername));

        if (oldUser != null) {
            throw new UserExistsException("There is such a user already");
        }

        User user = userRepository.findByUsername(String.valueOf(username));

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        user.setUsername(newUsername);
        userRepository.save(user);
        logout();

        return ResponseEntity.ok("Successfully changed username");
    }

    public Optional<String> getUserRole(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return Optional.empty();
        }

        return Optional.of(user.getRole());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void migratePasswords() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String raw = user.getPassword();
            if (!raw.startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(raw));
                userRepository.save(user);
            }
        }
    }
}