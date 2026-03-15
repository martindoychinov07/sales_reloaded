/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */

package com.reloaded.sales.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.reloaded.sales.service.IntegrationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Tag(name = "utility", description = "integration service")
@RestController
@RequestMapping("/api/utility")
@RequiredArgsConstructor
public class UtilityController {

  private final IntegrationService service; // Service responsible for external API calls

  /**
   * Sends a request to an external API and returns JSON response.
   * <p>
   * api    - identifies which external API to call
   * params - parameters passed to the external API
   */
  @GetMapping(
    value = "/json/{api}/{params}",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  public JsonNode requestJson(
    @PathVariable String api,
    @PathVariable String params
  ) {
    return service.requestJson(api, params);
  }

  @PostMapping(
    value = "/xlsx",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Generated XLSX file",
      content = @Content(
        mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
        schema = @Schema(type = "string", format = "binary")
      )
    )
  })
  public ResponseEntity<byte[]> requestXlsx(
    @RequestParam("name") String name,
    @RequestParam(value = "sep", required = false) String sep,
    @RequestParam(value = "eol", required = false) String eol,
    @RequestPart("blob") MultipartFile blob
  ) throws IOException {

    if (blob.isEmpty()) {
      return ResponseEntity.badRequest().body(null);
    }

    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Sheet1");

    // Configure CSV parser
    CSVFormat.Builder builder = CSVFormat.DEFAULT.builder();
    builder.setQuote('"');
    if (sep != null) {
      builder.setDelimiter(sep.charAt(0));
    }
    if (eol != null) {
      builder.setRecordSeparator(eol);
    }
    CSVFormat csvFormat = builder.get();

    try (Reader reader = new InputStreamReader(blob.getInputStream(), StandardCharsets.UTF_8)) {
      Iterable<CSVRecord> records = csvFormat.parse(reader);
      int rowNum = 0;
      int maxCol = 0;
      for (CSVRecord record : records) {
        Row row = sheet.createRow(rowNum++);
        for (int i = 0; i < record.size(); i++) {
          if (maxCol < i) {
            maxCol = i;
          }
          Cell cell = row.createCell(i);

          String raw = record.get(i);

          if (raw == null || raw.isBlank()) {
            cell.setBlank();
            continue;
          }

          String value = raw.trim();

          /* keep leading-zero values as text */
          boolean startsWithZeroWithoutDecimal =
            value.length() > 1 &&
              value.startsWith("0") &&
              !value.contains(".") &&
              !value.contains(",");

          if (startsWithZeroWithoutDecimal) {
            cell.setCellValue(value);
            continue;
          }

          /* normalize decimal separator */
          String normalized = value.replace(',', '.');

          /* simple numeric check */
          if (!normalized.matches("-?\\d+(\\.\\d+)?")) {
            cell.setCellValue(value);
            continue;
          }

          /* it is a number */
          double number = Double.parseDouble(normalized);
          cell.setCellValue(number);

          /* detect and apply format */
          String excelFormat = detectExcelNumberFormat(value);

          CellStyle style = workbook.createCellStyle();
          DataFormat dataFormat = workbook.createDataFormat();
          style.setDataFormat(dataFormat.getFormat(excelFormat));
          cell.setCellStyle(style);
        }
      }

      // auto-size columns
      for (int i = 0; i <= maxCol; i++) {
        sheet.autoSizeColumn(i);
      }
    }

    // Write XLSX to byte array
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();

    // Encode filename to ensure proper handling of spaces and special characters
    String encodedFilename = URLEncoder.encode(name + ".xlsx", StandardCharsets.UTF_8)
      .replaceAll("\\+", "%20");

    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename*=UTF-8''" + encodedFilename)
      .body(out.toByteArray());
  }

  private static String detectExcelNumberFormat(String original) {

    // decimal separator can be '.' or ','
    int dot = original.indexOf('.');
    int comma = original.indexOf(',');

    int sepIndex = Math.max(dot, comma);

    // integer
    if (sepIndex == -1) {
      return "0";
    }

    // decimal count
    int decimals = original.length() - sepIndex - 1;

    if (decimals <= 0) {
      return "0";
    }

    // build format like 0.00 / 0.000 etc

    return "0." + "0".repeat(decimals);
  }

}
