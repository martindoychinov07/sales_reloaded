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

  public Contact create(Contact contact) {
    return contactRepository.save(contact);
  }

  public Contact update(Contact changes) {
    Contact entity = contactRepository
      .findById(changes.getContactId())
      .orElseThrow(() -> new NotFound("Contact not found"));

    BeanUtils.copyProperties(changes, entity,Contact.Fields.contactId, Contact.Fields.contactState);

    return contactRepository.save(entity);
  }

  public void delete(Integer id) {
    Contact contact = contactRepository.findById(id)
      .orElseThrow(() -> new NotFound("Contact not found"));

    contactRepository.delete(contact);
  }

  public Contact getById(Integer id) {
    return contactRepository.findById(id).orElseThrow(() -> new NotFound("contact"));
  }

  public Page<Contact> findAllByNameLocationCode(String name, String location, String code, Pageable paging) {
    Contact probe = Contact.builder()
      .contactName(name)
      .contactLocation(location)
      .contactCode(code)
      .contactState(ContactState.active)
      .build();

    final GenericPropertyMatchers match = new GenericPropertyMatchers();
    final Contact.Fields field = new Contact.Fields();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(field.contactName, match.contains().ignoreCase())
      .withMatcher(field.contactLocation, match.contains().ignoreCase())
      .withMatcher(field.contactCode, match.contains().ignoreCase())
      .withMatcher(field.contactState, match.exact());

    Example<Contact> example = Example.of(probe, matcher);
    return contactRepository.findAll(example, paging);
  }

//  public <P> Page<P> findAllByNameLocationCode(String name, String location, String code, Pageable paging, Class<P> projection) {
//    Contact probe = Contact.builder()
//      .cName(name)
//      .cLocation(location)
//      .cCode(code)
//      .cState(1)
//      .build();
//
//    GenericPropertyMatchers match = new GenericPropertyMatchers();
//    ExampleMatcher matcher = ExampleMatcher
//      .matchingAll()
//      .withIgnoreNullValues()
//      .withMatcher(Contact.Fields.cName, match.contains().ignoreCase())
//      .withMatcher(Contact.Fields.cLocation, match.contains().ignoreCase())
//      .withMatcher(Contact.Fields.cCode, match.contains().ignoreCase())
//      .withMatcher(Contact.Fields.cState, match.exact());
//
//    Example<Contact> example = Example.of(probe, matcher);
//    return contactRepository.findAllBy(example, paging, projection);
//  }

}
