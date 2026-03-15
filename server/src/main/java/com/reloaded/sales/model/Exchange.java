/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.model;

import com.reloaded.sales.util.NormalizeText;
import com.reloaded.sales.util.TextNormalizationListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "exchange")
@EntityListeners(TextNormalizationListener.class)
public class Exchange extends AutoAuditedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_id_gen")
  @SequenceGenerator(name = "exchange_id_gen", sequenceName = "exchange_sequence", allocationSize = 1)
  @Column(name = "x_id", nullable = false)
  private Integer exchangeId;

  @Column(name = "x_date")
  private OffsetDateTime exchangeDate;

  @Size(max = 3)
  @NotNull
  @Column(name = "x_target", nullable = false, length = 3)
  @NormalizeText
  private String exchangeTarget;

  @Size(max = 3)
  @NotNull
  @Column(name = "x_source", nullable = false, length = 3)
  @NormalizeText
  private String exchangeSource;

  @NotNull
  @Column(name = "x_rate", nullable = false, precision = 16, scale = 4)
  private BigDecimal exchangeRate;

  public static String getFxKey(String target, String source) {
    return target + "/" + source;
  }
}