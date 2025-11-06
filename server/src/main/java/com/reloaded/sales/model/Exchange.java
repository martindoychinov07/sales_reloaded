package com.reloaded.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "exchange")
public class Exchange {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_id_gen")
  @SequenceGenerator(name = "exchange_id_gen", sequenceName = "exchange_sequence", allocationSize = 1)
  @Column(name = "x_id", nullable = false)
  private Integer exchangeId;

  @Column(name = "x_date")
  private Instant exchangeDate;

  @Size(max = 3)
  @NotNull
  @Column(name = "x_base", nullable = false, length = 3)
  private String exchangeBase;

  @Size(max = 3)
  @NotNull
  @Column(name = "x_target", nullable = false, length = 3)
  private String exchangeTarget;

  @NotNull
  @Column(name = "x_rate", nullable = false, precision = 16, scale = 4)
  private BigDecimal exchangeRate;

}