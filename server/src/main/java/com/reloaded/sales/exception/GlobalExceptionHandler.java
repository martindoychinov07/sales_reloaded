/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Map<String, String>> handleAlreadyExists(AlreadyExistsException ex) {
    Map<String, String> body = new HashMap<>();
    body.put("type", ex.getClass().getName());
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleAllOtherExceptions(Exception ex) {
    log.error("Unhandled Exception", ex);
    Map<String, String> body = new HashMap<>();
    body.put("type", ex.getClass().getName());
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
  }

  @ExceptionHandler(Throwable.class)
  public Mono<ResponseEntity<String>> handle(Throwable e) {
    log.error("Global exception caught:", e);
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body("Exception: " + e.getMessage()));
  }

}