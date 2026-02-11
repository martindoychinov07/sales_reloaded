package com.reloaded.sales.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.reloaded.sales.service.IntegrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "request", description = "integration service") // Swagger documentation
@RestController // Marks this as REST controller
@RequestMapping("/api/request") // Base URL for integration endpoints
@RequiredArgsConstructor // Generates constructor for final fields
public class IntegrationController {

  private final IntegrationService service; // Service responsible for external API calls

  /**
   * Sends a request to an external API and returns JSON response.
   *
   * api    - identifies which external API to call
   * params - parameters passed to the external API
   */
  @GetMapping(
          value = "/json/{api}/{params}",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public JsonNode requestJson(
          @PathVariable String api,
          @PathVariable String params
  ) {
    return service.requestJson(api, params);
  }

}
