package com.reloaded.sales.repository;

import com.reloaded.sales.model.AppUser;
import com.reloaded.sales.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
  Optional<RefreshToken> findByRefreshToken(String token);
  Optional<RefreshToken> findByRefreshUsername(String username);
  int deleteByRefreshUsername(String username);
}
