package com.reloaded.sales;

import com.reloaded.sales.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@EnableWebSecurity
public class SecurityConfig {

  private final AppUserService appUserService;

  public SecurityConfig(AppUserService appUserService) {
    this.appUserService = appUserService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      // CORS (only if SPA is separated)
      .cors(Customizer.withDefaults())

      // CSRF (enabled)
      .csrf(csrf -> csrf
        .csrfTokenRepository(
          CookieCsrfTokenRepository.withHttpOnlyFalse()
        )
      )

      // Authorization
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          "/",
          "/error",
          "/auth/csrf",
          "/login",
          "/logout",
          "/index.html",
          "/*.svg",
          "/assets/**",
          "/app/**",
          "/swagger-ui/**",
          "/v3/api-docs/**",
          "/api/translation/findTranslation"
        ).permitAll()
        .anyRequest().authenticated()
      )

      // UserDetails service
      .userDetailsService(appUserService)

      // Form login (session-based)
      .formLogin(form -> form
        .loginProcessingUrl("/login")
        .successHandler((req, res, auth) -> res.setStatus(200))
        .failureHandler((req, res, ex) -> res.setStatus(401))
      )

      // Logout
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
      );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(
      "http://localhost:5173"   // dev SPA
    ));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Content-Type", "X-XSRF-TOKEN"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
      new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }

}
