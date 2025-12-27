package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.RefreshToken;
import com.reloaded.sales.repository.RefreshTokenRepository;
import com.reloaded.sales.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

  private final JwtUtil jwtUtil;
  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenService(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
    this.jwtUtil = jwtUtil;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public RefreshToken createRefreshToken(UserDetails userDetails) {
    Optional<RefreshToken> found = refreshTokenRepository.findByRefreshUsername(userDetails.getUsername());
    RefreshToken refreshToken = found.orElse(new RefreshToken());
    refreshToken.setRefreshUsername(userDetails.getUsername());
    refreshToken.setRefreshToken(jwtUtil.generateRefreshToken(userDetails));
    refreshToken.setRefreshExpireDate(jwtUtil.extractExpiration(refreshToken.getRefreshToken()).toInstant());

    return refreshTokenRepository.save(refreshToken);
  }

  public RefreshToken validate(String tokenValue) {
    RefreshToken token = refreshTokenRepository.findByRefreshToken(tokenValue)
      .orElseThrow(() -> new NotFound("Invalid refresh token"));

    if (token.getRefreshExpireDate().isBefore(Instant.now())) {
      refreshTokenRepository.delete(token);
      throw new RuntimeException("Refresh token expired");
    }

    return token;
  }

  @Transactional
  public void deleteByUsername(String username) {
    refreshTokenRepository.deleteByRefreshUsername(username);
  }
}