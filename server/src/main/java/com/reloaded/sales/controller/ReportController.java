package com.reloaded.sales.controller;

import com.reloaded.sales.dto.ReportDto;
import com.reloaded.sales.dto.filter.ReportFilter;
import com.reloaded.sales.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for report generation and retrieval.
 * Currently provides a paginated endpoint for fetching reports
 * based on filtering criteria.
 */
@Tag(name = "report", description = "report service") // Swagger documentation
@RestController
@RequestMapping("/api/report") // Base URL for report endpoints
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService; // Service handling report logic

  /**
   * Returns a paginated list of reports based on filter criteria.
   *
   * @param reportFilter filter parameters (date range, type, etc.)
   * @return paginated list of ReportDto
   */
  @GetMapping(
          value = "/",
          produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  @Operation(operationId = "findReport")
  public Page<ReportDto> find(@ParameterObject @ModelAttribute ReportFilter reportFilter) {
    return reportService.findReport(reportFilter);
  }

}
