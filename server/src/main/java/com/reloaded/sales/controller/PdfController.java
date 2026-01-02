package com.reloaded.sales.controller;

import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.OrderFormService;
import com.reloaded.sales.service.PdfService;
import com.reloaded.sales.dto.PrintVariant;
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
  public ResponseEntity<byte[]> getOrderPdf(
    @PathVariable Integer orderId,
    @RequestParam String lang,
    @RequestParam String subtitle
    ) throws IOException {
    OrderForm orderForm = orderFormService.getOrderById(orderId);
    byte[] pdf = pdfService.generateInvoicePdf(
      orderForm,
       new PrintVariant(lang, subtitle)
    );

    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_PDF)
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "inline; filename=print_" + orderId + ".pdf")
      .body(pdf);
  }
}