package com.reloaded.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFound.class)
  public ResponseEntity<Map<String, String>> handleNotFound(NotFound ex) {
    Map<String, String> body = new HashMap<>();
    body.put("type", ex.getClass().getName());
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(AlreadyReported.class)
  public ResponseEntity<Map<String, String>> handleAlreadyReported(AlreadyReported ex) {
    Map<String, String> body = new HashMap<>();
    body.put("type", ex.getClass().getName());
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleAllOtherExceptions(Exception ex) {
    Map<String, String> body = new HashMap<>();
    body.put("type", ex.getClass().getName());
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
  }

}
