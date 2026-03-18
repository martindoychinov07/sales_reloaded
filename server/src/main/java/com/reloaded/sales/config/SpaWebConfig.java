/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

  /**
   * Configures forwarding for SPA (Single Page Application) routes.
   *
   * Any URL under /app/* should be handled by the frontend (index.html),
   * so Spring forwards it instead of trying to find a backend endpoint.
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // Forward /app/... routes (with any path) to index.html
    registry.addViewController("/app/{*path}") // matches /app/home, /app/users/1, etc.
            .setViewName("forward:/index.html");

    // Forward /app itself to index.html
    registry.addViewController("/app")
            .setViewName("forward:/index.html");
  }
}
