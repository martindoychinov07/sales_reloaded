package com.reloaded.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration // Marks this as a Spring configuration class
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
// Enables Spring Data support for web (e.g., Pageable in controllers)
// VIA_DTO: serializes page info through DTOs instead of exposing entity directly
public class WebConfig {

  /**
   * Configures CORS for the application
   * Allows frontend SPA (on localhost:3001) to call backend APIs
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:3001") // frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allowed HTTP methods
                .allowCredentials(true); // allow cookies (for sessions or CSRF)
      }
    };
  }

  /**
   * Provides a WebClient bean for making HTTP requests to other services
   */
  @Bean
  public WebClient webClient() {
    return WebClient.builder().build();
  }
}
