package com.reloaded.sales.config;

import com.reloaded.sales.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity // Enables Spring Security for the app
public class SecurityConfig {

  private final AppUserService appUserService;

  public SecurityConfig(AppUserService appUserService) {
    this.appUserService = appUserService;
  }

  /**
   * Defines the security filter chain: CORS, CSRF, authentication, login/logout
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            // Enable CORS (important if frontend SPA is served from a different origin)
            .cors(Customizer.withDefaults())

            // Enable CSRF protection using cookies
            .csrf(csrf -> csrf
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse() // allows frontend JS to read CSRF token
                    )
            )

            // Configure URL-based authorization
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/", "/error", "/auth/csrf", "/login", "/logout",
                            "/index.html", "/static/**", "/*.svg", "/assets/**",
                            "/app/**", "/swagger-ui/**", "/v3/api-docs/**",
                            "/api/translation/find"
                    ).permitAll() // public endpoints
                    .anyRequest().authenticated() // all other requests require authentication
            )

            // Set custom user service for authentication
            .userDetailsService(appUserService)

            // Configure form login (session-based)
            .formLogin(form -> form
                    .loginProcessingUrl("/login") // URL to submit username/password
                    .successHandler((req, res, auth) -> res.setStatus(200)) // return 200 on success
                    .failureHandler((req, res, ex) -> res.setStatus(401)) // return 401 on failure
            )

            // Configure logout
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler((req, res, auth) -> res.setStatus(200)) // return 200 after logout
            );

    return http.build();
  }

  /**
   * Password encoder bean using BCrypt
   * Important for hashing passwords securely
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures CORS for frontend SPA
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(
            "http://localhost:5173"   // frontend dev server
    ));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Content-Type", "X-XSRF-TOKEN"));
    config.setAllowCredentials(true); // allow cookies (for sessions / CSRF)

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config); // apply to all endpoints

    return source;
  }

}
