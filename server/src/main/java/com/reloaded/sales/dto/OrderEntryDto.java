/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for {@link com.reloaded.sales.model.OrderEntry}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntryDto {
  private Integer entryId;
  private Integer entryVersion;
  private Integer entryRow;
  private EntryProductDto entryProduct;
  @Size(max = 50)
  private String entryBarcode;
  @Size(max = 50)
  private String entryCode;
  @Size(max = 200)
  private String entryLabel;
  private Integer entryUnits;
  @Size(max = 30)
  private String entryMeasure;
  private BigDecimal entryAvailable;
  private BigDecimal entryQuantity;
  private BigDecimal entryPrice;
  private BigDecimal entryDiscountPct;
  private BigDecimal entryDiscount;
  private BigDecimal entrySum;
  private BigDecimal entryTax;
  private BigDecimal entryTotal;
}