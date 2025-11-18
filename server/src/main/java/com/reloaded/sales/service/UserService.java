package com.reloaded.sales.service;

import com.reloaded.sales.dto.UserDto;
import com.reloaded.sales.exception.*;
import com.reloaded.sales.model.User;
import com.reloaded.sales.security.JwtUtil;
import com.reloaded.sales.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger loginLogger = LoggerFactory.getLogger(UserService.class);

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User signUp(User u) {
        if (userRepository.findByUsername(u.getUsername()).isPresent()) {
            throw new AlreadyReported("User already exists");
        }

        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public String login(User u, HttpServletRequest request) {
        User dbUser = userRepository.findByUsername(u.getUsername())
                .orElseThrow(() -> new NotFound("User not found"));

        boolean check = passwordEncoder.matches(u.getPassword(), dbUser.getPassword());
        String clientIp = request.getRemoteAddr();

        if (!check) {
            loginLogger.warn("LOGIN FAILED: user={}, ip={}, time={}",
                    u.getUsername(), clientIp, LocalDateTime.now());

            throw new AlreadyReported("Invalid credentials");
        }

        loginLogger.info("LOGIN SUCCESS: user={}, ip={}, time={}",
        dbUser.getUsername(), clientIp, LocalDateTime.now());

        return jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole());
    }
//
//    public void logout(){
//        if (!isLoggedIn()) {
//            throw new AlreadyReported("User already logged out");
//        }
//
//        session.invalidate();
//    }

    public void delete(String username) {
        String currentUsername = getCurrentUsername();

        if (!currentUsername.equals(username)) {
            throw new AlreadyReported("Cannot delete other users");
        }

        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFound("User not found"));

        userRepository.delete(dbUser);
    }

    public void changePassword(String newPassword) {
        String username = getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFound("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void changeUsername(String newUsername) {
        String currentUsername = getCurrentUsername();

        if (userRepository.findByUsername(newUsername).isPresent()) {
            throw new AlreadyReported("Username already exists");
        }

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFound("User not found"));

        user.setUsername(newUsername);
        userRepository.save(user);

        // Inform frontend: user should request a new JWT after username change
    }


    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new AlreadyReported("User is not logged in");
        }
        return auth.getName();
    }


//    @Transactional(readOnly = true)
//    public Optional<String> getUserRole(String username) {
//        User user = userRepository.findByUsername(username);
//        if(user == null) {
//            return Optional.empty();
//        }
//
//        return Optional.of(user.getRole());
//    }


    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getRole(), u.getCode()))
                .collect(Collectors.toList());
    }

//    public Optional<User> getCurrentUser() {
//        Long userId = (Long) session.getAttribute("userId");
//
//        if (userId == null) {
//            return Optional.empty();
//        }
//
//        return userRepository.findById(userId);
//    }
}
