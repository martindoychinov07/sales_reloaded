/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

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
