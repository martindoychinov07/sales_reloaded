package com.reloaded.sales.repository;

import com.reloaded.sales.dto.OrderFormView;
import com.reloaded.sales.model.OrderForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface ReportRepository extends JpaRepository<OrderForm, Integer> {

  Page<OrderFormView> findByOrderDateAfter(
    OffsetDateTime start,
    Pageable pageable
  );

  Page<OrderFormView> findByOrderDateBetween(
    OffsetDateTime start,
    OffsetDateTime end,
    Pageable pageable
  );

  Page<OrderFormView> findByOrderDateAfterAndOrderCustomerContactNameContainingIgnoreCase(
    OffsetDateTime start,
    String customerName,
    Pageable pageable
  );

  Page<OrderFormView> findByOrderDateBetweenAndOrderCustomerContactNameContainingIgnoreCase(
    OffsetDateTime start,
    OffsetDateTime end,
    String customerName,
    Pageable pageable
  );

}

