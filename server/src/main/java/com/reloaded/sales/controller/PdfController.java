package com.reloaded.sales.controller;

import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.OrderFormService;
import com.reloaded.sales.service.PdfService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "pdf", description = "pdf service")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/pdf")
public class PdfController {

  private final PdfService pdfService;
  private final OrderFormService orderFormService;

  public PdfController(PdfService pdfService, OrderFormService orderFormService) {
    this.pdfService = pdfService;
    this.orderFormService = orderFormService;
  }

  @GetMapping("/{orderId}")
  @ApiResponse(
    responseCode = "200",
    description = "PDF",
    content = @Content(
      mediaType = MediaType.APPLICATION_PDF_VALUE,
      schema = @Schema(type = "string", format = "binary")
    )
  )
  public ResponseEntity<byte[]> getOrderPdf(
    @RequestHeader("Content-Type") String contentType,
    @PathVariable Integer orderId
  ) throws IOException {
    OrderForm orderForm = orderFormService.getOrderById(orderId);
    byte[] pdf = pdfService.generateInvoicePdf(orderForm);

    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_PDF)
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=print_" + orderId + ".pdf")
      .body(pdf);
  }
}