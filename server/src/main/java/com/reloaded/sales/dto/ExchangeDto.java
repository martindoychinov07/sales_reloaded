package com.reloaded.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.reloaded.sales.model.Exchange}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDto {
  private Integer exchangeId;
  private Instant exchangeDate;
  @NotNull
  @Size(max = 3)
  private String exchangeBase;
  @NotNull
  @Size(max = 3)
  private String exchangeTarget;
  @NotNull
  private BigDecimal exchangeRate;
}