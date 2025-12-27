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

import java.util.List;
import java.util.Optional;

@Tag(name = "contact", description = "contact service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/contact")
public class ContactController {

  private final ContactService contactService;
  private final ModelMapper modelMapper;

  /**
   *
   * @param contactService
   */
  public ContactController(
    ContactService contactService
  ) {
    this.contactService = contactService;
    this.modelMapper = new ModelMapper();
  }

  /**
   *
   * @param contactDto
   * @return
   */
  @PostMapping(
    value = "/createContact",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ContactDto createContact(
    @RequestBody ContactDto contactDto
  ) {
    return toDto(contactService.createContact(toEntity(contactDto)));
  }

  @PutMapping(
    value = "/updateContact",
    consumes = "application/json",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ContactDto updateContact(
    @RequestBody ContactDto contactDto
  ) {
    return toDto(contactService.updateContact(toEntity(contactDto)));
  }

  @DeleteMapping(
    value = "/deleteContact",
    consumes = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public void deleteContact(
    @RequestBody int id
  ) {
    contactService.deleteContact(id);
  }

  @GetMapping(
    value = "/findContact",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public Page<ContactDto> findContact(
    @RequestParam Optional<String> contactName,
    @RequestParam Optional<String> contactLocation,
    @RequestParam Optional<String> contactCode1,
    @RequestParam Optional<String> contactCode2,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<String> sort,
    @RequestParam Optional<Sort.Direction> direction
  ) {
    PageRequest paging = PageRequest.of(
        page.orElse(0),
        size.orElse(20),
        sort.isPresent()
          ? Sort.by(
            Sort.by(direction.orElse(Sort.Direction.ASC), sort.get()).getOrderFor(sort.get()),
            Sort.Order.asc(Contact.Fields.contactId)
          )
          : Sort.by(Sort.Order.asc(Contact.Fields.contactId)
        )
      );

    return contactService
      .findContactByNameLocationCode(
        contactName.orElse(null),
        contactLocation.orElse(null),
        contactCode1.orElse(null),
        contactCode2.orElse(null),
        paging
      ).map(this::toDto);
  }

  @GetMapping(
    value = "/findLocation",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public List<String> findLocation(
    @RequestParam String contactCode1
  ) {
    return contactService.findContactLocation(contactCode1);
  }

  @GetMapping(
    value = "/{id}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public ContactDto getContactById(
    @PathVariable int id
  ) {
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
