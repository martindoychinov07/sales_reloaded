package com.reloaded.sales.controller;

import com.reloaded.sales.dto.OrderFormView;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Optional;

@Tag(name = "report", description = "report service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/report")
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping(
    value = "/findReport",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public Page<OrderFormView> findReport(
    @RequestParam Optional<OffsetDateTime> startOrderDate,
    @RequestParam Optional<OffsetDateTime> endOrderDate,
    @RequestParam Optional<String> customerName,
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
            Sort.Order.asc(OrderForm.Fields.orderId)
          )
          : Sort.by(Sort.Order.asc(OrderForm.Fields.orderId)
        )
      );

    return reportService
      .findOrderFormView(
        startOrderDate.orElse(null),
        endOrderDate.orElse(null),
        customerName.orElse(null),
        paging
      );
  }

}
