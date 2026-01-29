/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.util;

import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class CsvExport {

  public static <T> void export(
    HttpServletResponse response,
    String fileName,
    CsvColumnRegistry<T> registry,
    List<String> selectedKeys,
    Function<Pageable, Page<T>> pageSupplier
  ) throws IOException {

    List<CsvColumn<T>> selectedColumns = registry.resolve(selectedKeys);

    if (selectedColumns.isEmpty()) {
      throw new IllegalArgumentException("No valid columns selected");
    }

    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setContentType("text/csv");
    response.setHeader(
      HttpHeaders.CONTENT_DISPOSITION,
      "attachment; filename=\"" + fileName + "\""
    );

    try (Writer writer = response.getWriter();
         CSVWriter csv = new CSVWriter(writer)) {

      // Optional: UTF-8 BOM for Excel
      writer.write('\uFEFF');

      // Header
      csv.writeNext(
        selectedColumns.stream()
          .map(CsvColumn::label)
          .toArray(String[]::new)
      );

      // Pagination
      int page = 0;
      int size = 1000;
      Page<T> result;

      do {
        result = pageSupplier.apply(PageRequest.of(page, size));

        for (T row : result.getContent()) {
          csv.writeNext(toRow(row, selectedColumns));
        }

        page++;
      } while (result.hasNext());
    }
  }

  private static <T> String[] toRow(T obj, List<CsvColumn<T>> columns) {
    return columns.stream()
      .map(c -> Objects.toString(c.extractor().apply(obj), ""))
      .toArray(String[]::new);
  }
}
