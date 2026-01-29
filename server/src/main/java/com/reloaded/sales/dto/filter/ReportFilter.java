/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto.filter;

import com.reloaded.sales.model.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportFilter implements PageFilter {

  private OffsetDateTime fromDate;
  private OffsetDateTime toDate;
  private Long orderNum;
  private String customerName;
  private String customerLocation;
  private String productName;
  private Integer orderTypeId;
  private String orderPayment;
  private OrderState orderState;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
