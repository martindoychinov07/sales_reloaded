package com.reloaded.sales.repository;

import com.reloaded.sales.model.Contact;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

//  <P, S> Page<P> findAllBy(Example<S> example, Pageable pageable, Class<P> type);

}
