package com.reloaded.sales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // Forward all requests under /app/* (e.g., /app/home, /app/users/1) to the index.html
    registry.addViewController("/app/{*path}") // Matches paths that don't have a file extension
      .setViewName("forward:/index.html");

    // Also ensure /app itself forwards to index.html
    registry.addViewController("/app")
      .setViewName("forward:/index.html");
  }
}
