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

@Tag(name = "auth", description = "auth service")
@RestController
@RequestMapping("/auth")
@NoArgsConstructor
public class AuthController {

  @GetMapping(
    value = "/csrf",
    produces = "application/json"
  )
  public CsrfToken csrf(CsrfToken token) {
    return token;
  }

  @GetMapping(
    value = "/info",
    produces = "application/json"
  )
  public AuthUser info(
    @AuthenticationPrincipal AppUserDetails userDetails
  ) {
    return new AuthUser(userDetails.getUsername(), userDetails.getFullname());
  }

}
