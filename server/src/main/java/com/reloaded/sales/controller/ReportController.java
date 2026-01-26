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

@Tag(name = "report", description = "report service")
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

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
