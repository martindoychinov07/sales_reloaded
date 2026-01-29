/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto.filter;

import com.reloaded.sales.model.OrderEval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTypeFilter implements PageFilter {

  private Integer typeCounter;
  private OrderEval typeEval;
  private String typeNote;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
