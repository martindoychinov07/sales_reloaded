package com.reloaded.sales.controller;

import com.reloaded.sales.dto.PrintVariantDto;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.service.OrderFormService;
import com.reloaded.sales.service.PdfService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "pdf", description = "pdf service")
@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

  private final PdfService pdfService;
  private final OrderFormService orderFormService;

  @GetMapping("/{orderId}/{lang}/{sign}/{name}")
  public ResponseEntity<byte[]> getOrderPdf(
    @PathVariable Integer orderId,
    @PathVariable String lang,
    @PathVariable String sign,
    @PathVariable String name
    ) throws IOException {
    OrderForm orderForm = orderFormService.getOrderById(orderId);
    byte[] pdf = pdfService.generatePdf(orderForm, new PrintVariantDto(lang, sign));

    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_PDF)
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "inline; filename=" + name + ".pdf")
      .body(pdf);
  }
}