package com.reloaded.sales.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_entry")
public class OrderEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_entry_id_gen")
  @SequenceGenerator(name = "order_entry_id_gen", sequenceName = "entry_sequence", allocationSize = 1)
  @Column(name = "entry_id", nullable = false)
  private Integer entryId;

  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "order_row")
  private Integer orderRow;

  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "barcode", length = 50)
  private String barcode;

  @Column(name = "code", length = 50)
  private String code;

  @Column(name = "label", length = 200)
  private String label;

  @Column(name = "units")
  private Integer units;

  @Column(name = "measure", length = 30)
  private String measure;

  @Column(name = "available")
  private Integer available;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "price", precision = 16, scale = 4)
  private BigDecimal price;

  @Column(name = "discount", precision = 16, scale = 4)
  private BigDecimal discount;

  @Column(name = "tax", precision = 16, scale = 4)
  private BigDecimal tax;

  @Column(name = "ware_id", length = 40)
  private String wareId;

}