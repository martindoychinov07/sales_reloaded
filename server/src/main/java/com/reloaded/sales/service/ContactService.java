package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.model.ContactState;
import com.reloaded.sales.repository.ContactRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

@Service
@Transactional
public class ContactService {
  private final ContactRepository contactRepository;

  public ContactService(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  public Contact createContact(Contact contact) {
    contact.setContactState(ContactState.active);
    return contactRepository.save(contact);
  }

  public Contact updateContact(Contact changes) {
    Contact entity = contactRepository
      .findById(changes.getContactId())
      .orElseThrow(() -> new NotFound("Contact not found"));

    BeanUtils.copyProperties(changes, entity, Contact.Fields.contactId, Contact.Fields.contactState);

    return contactRepository.save(entity);
  }

  public void deleteContact(Integer id) {
    Contact contact = contactRepository.findById(id)
      .orElseThrow(() -> new NotFound("Contact not found"));
    contact.setContactState(ContactState.deleted);
    contactRepository.save(contact);
  }

  public Contact getContactById(Integer id) {
    return contactRepository.findById(id).orElseThrow(() -> new NotFound("contact"));
  }

  @Transactional(readOnly = true)
  public List<String> findContactLocation(String contactCode1) {
    return contactRepository.findContactLocationByContactCode1(contactCode1);
  }

  @Transactional(readOnly = true)
  public Page<Contact> findContactByNameLocationCode(
    String contactName,
    String contactLocation,
    String contactCode1,
    String contactCode2,
    Pageable paging
  ) {
    Contact probe = Contact.builder()
      .contactName(contactName)
      .contactLocation(contactLocation)
      .contactCode1(contactCode1)
      .contactCode2(contactCode2)
      .contactState(ContactState.active)
      .build();

    final GenericPropertyMatchers match = new GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Contact.Fields.contactName, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactLocation, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactCode1, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactCode2, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactState, match.exact());

    Example<Contact> example = Example.of(probe, matcher);
    return contactRepository.findAll(example, paging);
  }

}
