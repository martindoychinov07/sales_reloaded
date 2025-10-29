package com.reloaded.sales.model;

import com.reloaded.sales.model.Partner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "order_form")
public class OrderForm {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_form_id_gen")
  @SequenceGenerator(name = "order_form_id_gen", sequenceName = "order_sequence", allocationSize = 1)
  @Column(name = "order_id", nullable = false)
  private Integer id;

  @Column(name = "order_ref_id")
  private Integer orderRefId;

  @Column(name = "order_state")
  private Integer orderState;

  @Column(name = "order_date")
  private Instant orderDate;

  @Column(name = "order_book", length = 20)
  private String orderBook;

  @Column(name = "order_num")
  private Long orderNum;

  @Column(name = "order_type")
  private Integer orderType;

  @Column(name = "order_view", length = 200)
  private String orderView;

  @Column(name = "user_id")
  private Integer userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "supplier_id")
  private Partner supplier;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Partner customer;

  @Column(name = "note", length = 300)
  private String note;

  @Column(name = "order_tags", length = 500)
  private String orderTags;

  @Column(name = "payment_tags", length = 100)
  private String paymentTags;

  @Column(name = "availability")
  private Integer availability;

  @Column(name = "rows")
  private Integer rows;

  @Column(name = "vat", precision = 16, scale = 4)
  private BigDecimal vat;

  @Column(name = "ccy", length = 7)
  private String ccy;

  @Column(name = "rate", precision = 16, scale = 4)
  private BigDecimal rate;

  @Column(name = "total", precision = 16, scale = 4)
  private BigDecimal total;

  @Column(name = "total_tax", precision = 16, scale = 4)
  private BigDecimal totalTax;

}