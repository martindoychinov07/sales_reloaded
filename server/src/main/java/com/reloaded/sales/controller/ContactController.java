/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ContactDto;
import com.reloaded.sales.dto.filter.ContactFilter;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "contact", description = "contact service")
@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController implements CrudController<ContactDto, ContactFilter, Contact> {

  private final ContactService contactService;
  private final ModelMapper modelMapper;

  /**
   * Creates a new contact
   */
  @Operation(operationId = "createContact")
  @Override
  public ContactDto create(@RequestBody ContactDto contactDto) {
    return toDto(contactService.createContact(toEntity(contactDto)));
  }

  /**
   * Updates an existing contact
   */
  @Operation(operationId = "updateContact")
  @Override
  public ContactDto update(@PathVariable int id, @RequestBody ContactDto contactDto) {
    Contact contact = toEntity(contactDto);
    contact.setContactId(id); // Ensures ID comes from URL path
    return toDto(contactService.updateContact(contact));
  }

  /**
   * Deletes a contact by ID
   */
  @Operation(operationId = "deleteContact")
  @Override
  public void delete(@PathVariable int id) {
    contactService.deleteContact(id);
  }

  /**
   * Returns paginated list of contacts using filter criteria
   */
  @Operation(operationId = "findContact")
  @Override
  public Page<ContactDto> find(@ParameterObject @ModelAttribute ContactFilter filter) {
    return contactService.findContact(filter).map(this::toDto);
  }

  /**
   * Returns contact by ID
   */
  @Operation(operationId = "getContactById")
  @Override
  public ContactDto getById(@PathVariable int id) {
    return toDto(contactService.getContactById(id));
  }

  /**
   * Converts DTO to Entity
   */
  @Override
  public Contact toEntity(ContactDto dto) {
    return modelMapper.map(dto, Contact.class);
  }

  /**
   * Converts Entity to DTO
   */
  @Override
  public ContactDto toDto(Contact entity) {
    return modelMapper.map(entity, ContactDto.class);
  }

}
