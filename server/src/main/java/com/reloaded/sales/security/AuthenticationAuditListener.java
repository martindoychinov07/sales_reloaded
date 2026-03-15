/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthenticationAuditListener {

  private static final Logger authLog = LoggerFactory.getLogger("security.auth");

  private String currentSessionId() {
    var attrs = RequestContextHolder.getRequestAttributes();
    if (attrs instanceof ServletRequestAttributes sra) {
      return sra.getRequest().getSession(false) != null
        ? sra.getRequest().getSession(false).getId()
        : null;
    }
    return null;
  }

  private String currentIp() {
    var attrs = RequestContextHolder.getRequestAttributes();
    if (attrs instanceof ServletRequestAttributes sra) {
      return sra.getRequest().getRemoteAddr();
    }
    return null;
  }

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent event) {
    authLog.info("Login success user={} ip={} session={}",
      event.getAuthentication().getName(),
      currentIp(),
      currentSessionId());
  }

  @EventListener
  public void onFailure(AbstractAuthenticationFailureEvent event) {
    authLog.warn("Login failed user={} ip={} reason={}",
      event.getAuthentication().getName(),
      currentIp(),
      event.getException().getClass().getSimpleName());
  }

}

