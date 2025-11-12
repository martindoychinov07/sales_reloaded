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

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

@Service
@Transactional
public class ContactService {
  private final ContactRepository contactRepository;

  public ContactService(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  public Contact createContact(Contact contact) {
    return contactRepository.save(contact);
  }

  public Contact updateContact(Contact changes) {
    Contact entity = contactRepository
      .findById(changes.getContactId())
      .orElseThrow(() -> new NotFound("Contact not found"));

    BeanUtils.copyProperties(changes, entity,Contact.Fields.contactId, Contact.Fields.contactState);

    return contactRepository.save(entity);
  }

  public void deleteContact(Integer id) {
    Contact contact = contactRepository.findById(id)
      .orElseThrow(() -> new NotFound("Contact not found"));

    contactRepository.delete(contact);
  }

  public Contact getContactById(Integer id) {
    return contactRepository.findById(id).orElseThrow(() -> new NotFound("contact"));
  }

  public Page<Contact> findContactByNameLocationCode(String name, String location, String code, Pageable paging) {
    Contact probe = Contact.builder()
      .contactName(name)
      .contactLocation(location)
      .contactCode(code)
      .contactState(ContactState.active)
      .build();

    final GenericPropertyMatchers match = new GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Contact.Fields.contactName, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactLocation, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactCode, match.contains().ignoreCase())
      .withMatcher(Contact.Fields.contactState, match.exact());

    Example<Contact> example = Example.of(probe, matcher);
    return contactRepository.findAll(example, paging);
  }

}
