/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(
    MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
      errors.put(error.getField(), error.getDefaultMessage())
    );
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(
    DataIntegrityViolationException ex) {

    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);

    problem.setTitle("Data integrity violation");
    problem.setDetail("The request violates a database constraint.");

    String constraint = extractConstraintName(ex);
    if (constraint != null) {
      problem.setProperty("constraint", constraint);
    }

    return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
  }

  private String extractConstraintName(DataIntegrityViolationException ex) {
    Throwable cause = ex.getCause();
    // Hibernate-specific (most common with JPA)
    if (cause instanceof org.hibernate.exception.ConstraintViolationException cve) {
      return cve.getConstraintName();
    }
    return null;
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