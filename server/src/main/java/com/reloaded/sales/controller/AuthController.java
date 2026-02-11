package com.reloaded.sales.controller;

import com.reloaded.sales.security.AppUserDetails;
import com.reloaded.sales.security.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "auth service") // Swagger documentation
@RestController // Marks this as a REST controller
@RequestMapping("/auth") // Base path for authentication-related endpoints
@NoArgsConstructor
public class AuthController {

  /**
   * Returns the CSRF token to the frontend.
   *
   * Used by SPA to read the token and send it in future requests.
   */
  @GetMapping(
          value = "/csrf",
          produces = "application/json"
  )
  public CsrfToken csrf(CsrfToken token) {
    return token; // Spring automatically injects the current CSRF token
  }

  /**
   * Returns information about the currently authenticated user.
   */
  @GetMapping(
          value = "/info",
          produces = "application/json"
  )
  public AuthUser info(
          @AuthenticationPrincipal AppUserDetails userDetails
  ) {
    // Extracts user data from Spring Security context
    return new AuthUser(
            userDetails.getUsername(),
            userDetails.getFullname()
    );
  }

}
