package com.reloaded.sales.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Generic CRUD controller interface.
 *
 * D - DTO type
 * F - Filter type
 * E - Entity type
 *
 * Provides common REST endpoints for create, update, delete,
 * find (paginated), and getById operations.
 */
public interface CrudController<D, F, E> {

  /**
   * Creates a new resource
   */
  @PostMapping(
          value = "/",
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  D create(@RequestBody D dto);

  /**
   * Updates an existing resource by ID
   */
  @PutMapping(
          value = "/{id:\\d+}", // Only numeric IDs allowed
          consumes = "application/json",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  D update(@PathVariable int id, @RequestBody D dto);

  /**
   * Deletes resource by ID
   */
  @DeleteMapping(
          value = "/{id:\\d+}"
  )
  @ResponseStatus(HttpStatus.OK)
  void delete(@PathVariable int id);

  /**
   * Returns paginated list of resources using filter criteria
   */
  @GetMapping(
          value = "/find",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  Page<D> find(@ParameterObject @ModelAttribute F filter);

  /**
   * Returns resource by ID
   */
  @GetMapping(
          value = "/{id:\\d+}",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  D getById(@PathVariable int id);

  /**
   * Converts DTO to Entity
   */
  E toEntity(D dto);

  /**
   * Converts Entity to DTO
   */
  D toDto(E entity);
}
