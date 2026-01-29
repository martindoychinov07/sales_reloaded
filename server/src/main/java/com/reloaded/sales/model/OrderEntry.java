/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "order_entry")
public class OrderEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_entry_id_gen")
  @SequenceGenerator(name = "order_entry_id_gen", sequenceName = "order_entry_sequence", allocationSize = 1)
  @Column(name = "e_id", nullable = false)
  private Integer entryId;

//  @Column(name = "e_version")
//  private Integer entryVersion;

  @ManyToOne()
  @JoinColumn(name = "e_order_id", nullable = false)
  private OrderForm entryOrder;

  @Column(name = "e_row")
  private Integer entryRow;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "e_product_id")
  private Product entryProduct;

  @Size(max = 50)
  @Column(name = "e_barcode", length = 50)
  private String entryBarcode;

  @Size(max = 50)
  @Column(name = "e_code", length = 50)
  private String entryCode;

  @Size(max = 200)
  @Column(name = "e_label", length = 200)
  private String entryLabel;

  @Column(name = "e_units")
  private Integer entryUnits;

  @Size(max = 30)
  @Column(name = "e_measure", length = 30)
  private String entryMeasure;

  @Column(name = "e_available")
  private BigDecimal entryAvailable;

  @Column(name = "e_quantity")
  private BigDecimal entryQuantity;

  @Column(name = "e_price", precision = 16, scale = 4)
  private BigDecimal entryPrice;

  @Column(name = "e_discount_pct", precision = 16, scale = 4)
  private BigDecimal entryDiscountPct;

  @Column(name = "e_discount", precision = 16, scale = 4)
  private BigDecimal entryDiscount;

  @Column(name = "e_tax", precision = 16, scale = 4)
  private BigDecimal entryTax;

  @Column(name = "e_sum", precision = 16, scale = 4)
  private BigDecimal entrySum;

  @Column(name = "e_total", precision = 16, scale = 4)
  private BigDecimal entryTotal;

}