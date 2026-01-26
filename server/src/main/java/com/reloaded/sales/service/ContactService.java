package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.ContactFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Contact;
import com.reloaded.sales.model.ContactState;
import com.reloaded.sales.repository.ContactRepository;
import com.reloaded.sales.util.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.*;
import static com.reloaded.sales.util.ServiceUtils.anyLike;

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

  @Transactional(readOnly = true)
  public Contact getContactById(Integer id) {
    return contactRepository.findById(id).orElseThrow(() -> new NotFound("contact"));
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(Contact.Fields.contactName),
    Sort.Order.asc(Contact.Fields.contactLocation),
    Sort.Order.desc(Contact.Fields.contactId)
  );

  @Transactional(readOnly = true)
  public Page<Contact> findContact(ContactFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    Specification<Contact> spec = (root, query, cb) -> cb.conjunction();

    spec.and(eq(Contact.Fields.contactState, ContactState.active));
    spec.and(anyLike(filter.getContactLocation(), Contact.Fields.contactLocation));
    spec.and(anyLike(filter.getContactCode(), Contact.Fields.contactCode, Contact.Fields.contactCode1, Contact.Fields.contactCode2 ));
    spec.and(anyLike(filter.getContactText(), Contact.Fields.contactName, Contact.Fields.contactLocation, Contact.Fields.contactCode, Contact.Fields.contactCode1 ));

    return contactRepository.findAll(spec, paging);
  }

}
