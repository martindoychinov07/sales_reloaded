package com.reloaded.sales.repository;

import com.reloaded.sales.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {

  Page<Partner> findAllByNameContainsIgnoreCaseOrLocationContainingIgnoreCaseAndPartnerState(
    String name,
    String location,
    Integer partnerState,
    Pageable pageable
  );

}
