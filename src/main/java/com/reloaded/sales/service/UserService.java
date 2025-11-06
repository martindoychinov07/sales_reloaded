package com.reloaded.sales.service;

import com.reloaded.sales.dto.UserDto;
import com.reloaded.sales.exception.*;
import com.reloaded.sales.model.User;
import com.reloaded.sales.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    final UserRepository userRepository;
    private HttpSession session;

    public UserService(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger loginLogger = LoggerFactory.getLogger(UserService.class);

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User signUp(User u) {
        if (isLoggedIn()) {
            throw new AlreadyReported("User already logged in");
        }

        if (userRepository.findByUsername(u.getUsername()) != null) {
            throw new AlreadyReported("User already exists");
        }

        u.setPassword(passwordEncoder.encode(u.getPassword()));

        return userRepository.save(u);
    }

    public void login(User u, HttpServletRequest request) {
        if (isLoggedIn()) {
            throw new AlreadyReported("User already logged in");
        }

        User dbUser = userRepository.findByUsername(u.getUsername())
                .orElseThrow(() -> new NotFound("User not found"));

        boolean check = passwordEncoder.matches(u.getPassword(), dbUser.getPassword());
        String clientIp = request.getRemoteAddr();

        if (!check) {
            loginLogger.warn("LOGIN FAILED: user={}, ip={}, time={}",
                    u.getUsername(), clientIp, LocalDateTime.now());
        } else {
            session.setAttribute("userId", dbUser.getId());
            session.setAttribute("username", dbUser.getUsername());
            session.setMaxInactiveInterval(48 * 3600);

            loginLogger.info("LOGIN SUCCESS: user={}, ip={}, time={}",
                    dbUser.getUsername(), clientIp, LocalDateTime.now());
        }
    }

    public void logout(){
        if (!isLoggedIn()) {
            throw new AlreadyReported("User already logged out");
        }

        session.invalidate();
    }

    public void delete(String username) {

        if (isLoggedOut()) {
            throw new AlreadyReported("User already logged out");
        }

        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFound("User not found"));

        userRepository.delete(dbUser);
        session.invalidate();
    }

    public void changePassword(String newPassword) {
        Long userId = Optional.of((Long) session.getAttribute("userId")).orElseThrow(() ->
                new AlreadyReported("User logged out")
        );

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));

        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void changeUsername(String newUsername) {
        Long userId = Optional.of((Long) session.getAttribute("userId")).orElseThrow(() ->
                new AlreadyReported("User logged out")
        );

        User oldUser = userRepository.findByUsername(String.valueOf(newUsername))
                .orElseThrow(() -> new AlreadyReported("User already exists"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));

        user.setUsername(newUsername);
        userRepository.save(user);
        logout();
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

    public boolean isLoggedIn() {
        return session.getAttribute("userId") != null;
    }

    public boolean isLoggedOut() {
        return session.getAttribute("userId") == null;
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
