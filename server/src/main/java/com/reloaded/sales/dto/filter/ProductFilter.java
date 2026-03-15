/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.dto.filter;

import com.reloaded.sales.util.PageFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilter implements PageFilter {

  private String productText;
  private String productName;
  private String productNote;
  private BigDecimal fromAvailable;
  private BigDecimal toAvailable;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
