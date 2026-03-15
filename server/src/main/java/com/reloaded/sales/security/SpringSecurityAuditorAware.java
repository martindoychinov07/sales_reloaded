/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class SpringSecurityAuditorAware implements AuditorAware<Integer> {

  @Override
  public Optional<Integer> getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null
      || !authentication.isAuthenticated()
      || authentication.getPrincipal().equals("anonymousUser")) {
      return Optional.empty();
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof AppUserDetails userDetails) {
      return Optional.of(userDetails.getId());
    }

    return Optional.empty();
  }
}
