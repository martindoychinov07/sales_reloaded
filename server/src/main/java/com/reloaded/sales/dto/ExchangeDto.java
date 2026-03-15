/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.reloaded.sales.model.Exchange}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDto {
  private Integer exchangeId;
  private OffsetDateTime exchangeDate;
  @NotNull
  @Size(max = 3)
  private String exchangeTarget;
  @NotNull
  @Size(max = 3)
  private String exchangeSource;
  @NotNull
  private BigDecimal exchangeRate;
}