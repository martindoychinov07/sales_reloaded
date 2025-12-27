package com.reloaded.sales.service;

import com.reloaded.sales.dto.OrderFormView;
import com.reloaded.sales.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Transactional(readOnly = true)
public class ReportService {

  private final ReportRepository reportRepository;

  public ReportService(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  public Page<OrderFormView> findOrderFormView(
    OffsetDateTime startOrderDate,
    OffsetDateTime endOrderDate,
    String customerName,
    Pageable paging
  ) {
    if (customerName == null || customerName.isBlank()) {
      if (endOrderDate == null) {
        return reportRepository.findByOrderDateAfter(
          startOrderDate,
          paging
        );
      }
      else {
        return reportRepository.findByOrderDateBetween(
          startOrderDate,
          endOrderDate,
          paging
        );
      }
    }
    else {
      if (endOrderDate == null) {
        return reportRepository.findByOrderDateAfterAndOrderCustomerContactNameContainingIgnoreCase(
          startOrderDate,
          customerName,
          paging
        );
      }
      else {
        return reportRepository.findByOrderDateBetweenAndOrderCustomerContactNameContainingIgnoreCase(
          startOrderDate,
          endOrderDate,
          customerName,
          paging
        );
      }
    }
  }
}
