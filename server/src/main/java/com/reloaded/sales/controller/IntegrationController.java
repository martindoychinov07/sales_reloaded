package com.reloaded.sales.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.reloaded.sales.service.IntegrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "request", description = "integration service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/request")
public class IntegrationController {

  private final IntegrationService service;

  public IntegrationController(IntegrationService service) {
    this.service = service;
  }

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
