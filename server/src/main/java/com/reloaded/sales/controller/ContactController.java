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

  @Operation(operationId = "createContact")
  @Override
  public ContactDto create(@RequestBody ContactDto contactDto) {
    return toDto(contactService.createContact(toEntity(contactDto)));
  }

  @Operation(operationId = "updateContact")
  @Override
  public ContactDto update(@PathVariable int id, @RequestBody ContactDto contactDto) {
    Contact contact = toEntity(contactDto);
    contact.setContactId(id);
    return toDto(contactService.updateContact(contact));
  }

  @Operation(operationId = "deleteContact")
  @Override
  public void delete(@PathVariable int id) {
    contactService.deleteContact(id);
  }

  @Operation(operationId = "findContact")
  @Override
  public Page<ContactDto> find(@ParameterObject @ModelAttribute ContactFilter filter) {
    return contactService.findContact(filter).map(this::toDto);
  }

  @Operation(operationId = "getContactById")
  @Override
  public ContactDto getById(@PathVariable int id) {
    return toDto(contactService.getContactById(id));
  }

  @Override
  public Contact toEntity(ContactDto dto) {
    return modelMapper.map(dto, Contact.class);
  }

  @Override
  public ContactDto toDto(Contact entity) {
    return modelMapper.map(entity, ContactDto.class);
  }

}
