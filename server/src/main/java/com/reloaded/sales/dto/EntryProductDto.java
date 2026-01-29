/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto;

import com.reloaded.sales.model.Product;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryProductDto {
  private Integer productId;
  private Integer productVersion;
  private Integer productRefId;
  @Size(max = 200)
  private String productName;
  private Integer productUnits;
  @Size(max = 30)
  private String productMeasure;
  private Integer productAvailable;
}