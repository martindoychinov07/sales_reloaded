package com.reloaded.sales.controller;

import com.reloaded.sales.model.RefreshToken;
import com.reloaded.sales.security.*;
import com.reloaded.sales.service.AppUserService;
import com.reloaded.sales.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth", description = "auth service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final AppUserService appUserService;
  private final RefreshTokenService refreshTokenService;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AppUserService appUserService, RefreshTokenService refreshTokenService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.appUserService = appUserService;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping(
    value="/login",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public AuthResponse login(@RequestBody AuthRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    AppUserDetails user = appUserService.loadUserByUsername(request.getUsername());
    String accessToken = jwtUtil.generateAccessToken(user);
    String refreshToken = refreshTokenService.createRefreshToken(user).getRefreshToken();

    return new AuthResponse(user.getUsername(), user.getFullname(), accessToken, refreshToken, null);
  }

  @PostMapping(
    value="/refresh",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public AuthResponse refresh(@RequestBody RefreshRequest request) {
    try {
      RefreshToken refresh = refreshTokenService.validate(request.getRefreshToken());

      AppUserDetails user = appUserService.loadUserByUsername(refresh.getRefreshUsername());
      String newAccessToken = jwtUtil.generateAccessToken(user);

      return new AuthResponse(user.getUsername(), user.getFullname(), newAccessToken, refresh.getRefreshToken(), null);
    }
    catch (Exception ex) {
      return new AuthResponse(null, null, null, null, ex.getMessage());
    }
  }

  @PostMapping(
    value="/logout"
  )
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails user) {
    if (user != null) {
      refreshTokenService.deleteByUsername(user.getUsername());
    }
    return ResponseEntity.ok().build();
  }
}
