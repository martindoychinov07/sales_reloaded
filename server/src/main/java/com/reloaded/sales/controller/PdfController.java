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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * REST controller responsible for generating and returning PDF documents.
 *
 * This controller:
 *  - Loads an OrderForm by ID
 *  - Generates a PDF representation using PdfService
 *  - Returns the PDF as a byte array in the HTTP response
 */
@Tag(name = "pdf", description = "pdf service")
@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

  // Service responsible for generating PDF files
  private final PdfService pdfService;

  // Service responsible for retrieving order data
  private final OrderFormService orderFormService;

  /**
   * Generates and returns a PDF for a given order.
   *
   * @param orderId ID of the order to generate the PDF for
   * @param lang    Language of the PDF (e.g. "en", "bg")
   * @param sign    Indicates whether the PDF should include a signature
   * @param name    Desired file name (without extension)
   * @return        PDF file as byte array wrapped in ResponseEntity
   * @throws IOException if PDF generation fails
   */
  @GetMapping("/{orderId}/{lang}/{sign}/{name}")
  public ResponseEntity<byte[]> getOrderPdf(
          @PathVariable Integer orderId,
          @PathVariable String lang,
          @PathVariable String sign,
          @PathVariable String name
  ) throws IOException {

    // Fetch the order entity from the database
    OrderForm orderForm = orderFormService.getOrderById(orderId);

    // Generate the PDF using the order data and print configuration
    byte[] pdf = pdfService.generatePdf(
            orderForm,
            new PrintVariantDto(lang, sign)
    );

    // Encode filename to ensure proper handling of spaces and special characters
    String encodedFilename = URLEncoder.encode(name + ".pdf", StandardCharsets.UTF_8)
            .replaceAll("\\+", "%20");

    // Return PDF in HTTP response
    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF) // Set content type to PDF
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename*=UTF-8''" + encodedFilename // Display inline in browser
            )
            .body(pdf); // Attach PDF bytes as response body
  }
}
