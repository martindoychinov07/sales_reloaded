package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ContactDto;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.service.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "contact", description = "contact service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/contact")
public class ContactController {

  private final ContactService contactService;
  private final ModelMapper modelMapper;

  public ContactController(
    ContactService contactService
  ) {
    this.contactService = contactService;
    this.modelMapper = new ModelMapper();
  }

  @PostMapping(
    value = "/createContact",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ContactDto createContact(@RequestBody ContactDto contactDto) {
    return toDto(contactService.createContact(toEntity(contactDto)));
  }

  @PutMapping(
    value = "/updateContact",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ContactDto updateContact(@RequestBody ContactDto contactDto) {
    return toDto(contactService.updateContact(toEntity(contactDto)));
  }

  @DeleteMapping(
    value = "/deleteContact",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteContact(@RequestBody int id) {
    contactService.deleteContact(id);
  }

  @GetMapping(
    value = "/findContact",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public Page<ContactDto> findContact(
    @RequestParam String name,
    @RequestParam Optional<String> location,
    @RequestParam Optional<String> code,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<String> sort,
    @RequestParam Optional<Sort.Direction> direction
  ) {
    PageRequest paging = PageRequest.ofSize(size.orElse(20));
    if (page.isPresent()) {
      paging = paging.withPage(page.get());
    }
    if (sort.isPresent()) {
      paging = paging.withSort(direction.orElse(Sort.Direction.ASC), sort.get());
    }
    return contactService
      .findContactByNameLocationCode(
        name,
        location.orElse(null),
        code.orElse(null),
        paging
      ).map(this::toDto);
  }

  @GetMapping(
    value = "/{id}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ContactDto getContactById(@PathVariable int id) {
    return toDto(contactService.getContactById(id));
  }

  private Contact toEntity(ContactDto dto) {
    return modelMapper.map(
      dto,
      Contact.class
    );
  }

  private ContactDto toDto(Contact entity) {
    return modelMapper.map(
      entity,
      ContactDto.class
    );
  }

}
