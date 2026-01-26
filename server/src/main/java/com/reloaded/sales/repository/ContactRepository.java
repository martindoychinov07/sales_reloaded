package com.reloaded.sales.repository;

import com.reloaded.sales.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>, JpaSpecificationExecutor<Contact> {

  List<Contact> findAllByContactNameAndContactAddressAndContactCode1AndContactCode2OrderByContactIdDesc(
    String contactName,
    String contactAddress,
    String contactCode1,
    String contactCode2
  );

}
