package com.reloaded.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "order_form")
public class OrderForm {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_form_id_gen")
  @SequenceGenerator(name = "order_form_id_gen", sequenceName = "order_form_sequence", allocationSize = 1)
  @Column(name = "o_id", nullable = false)
  private Integer orderId;

  @Column(name = "o_version")
  private Integer orderVersion;

  @Column(name = "o_ref_id")
  private Integer orderRefId;

  @Column(name = "o_state")
  private OrderState orderState;

  @Column(name = "o_date")
  private OffsetDateTime orderDate;

  @Column(name = "o_num")
  private Long orderNum;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "o_type_id")
  private OrderType orderType;

  @Column(name = "o_user_id")
  private Integer orderUserId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "o_supplier_id")
  private Contact orderSupplier;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "o_customer_id")
  private Contact orderCustomer;

  @Size(max = 300)
  @Column(name = "o_note", length = 300)
  private String orderNote;

  @Size(max = 100)
  @Column(name = "o_resp", length = 100)
  private String orderResp;

  @Column(name = "o_resp_date")
  private OffsetDateTime orderRespDate;

  @Size(max = 100)
  @Column(name = "o_dlvd", length = 100)
  private String orderDlvd;

  @Column(name = "o_dlvd_date")
  private OffsetDateTime orderDlvdDate;

  @Size(max = 100)
  @Column(name = "o_rcvd", length = 100)
  private String orderRcvd;

  @Column(name = "o_rcvd_date")
  private OffsetDateTime orderRcvdDate;

  @Size(max = 100)
  @Column(name = "o_ref", length = 100)
  private String orderRef;

  @Size(max = 20)
  @Column(name = "o_payment", length = 20)
  private String orderPayment;

  @Column(name = "o_payment_date")
  private OffsetDateTime orderPaymentDate;

  @Column(name = "o_eval")
  private Integer orderEval;

  @Column(name = "o_date_1")
  private OffsetDateTime orderDate1;

  @Column(name = "o_date_2")
  private OffsetDateTime orderDate2;

  @Size(max = 100)
  @Column(name = "o_text_1", length = 100)
  private String orderText1;

  @Size(max = 100)
  @Column(name = "o_text_2", length = 100)
  private String orderText2;

  @Column(name = "o_rows")
  private Integer orderRows;

  @Column(name = "o_tax_pct", precision = 16, scale = 4)
  private BigDecimal orderTaxPct;

  @Size(max = 7)
  @Column(name = "o_ccp", length = 7)
  private String orderCcp;

  @Column(name = "o_rate", precision = 16, scale = 4)
  private BigDecimal orderRate;

  @Column(name = "o_sum", precision = 16, scale = 4)
  private BigDecimal orderSum;

  @Column(name = "o_tax", precision = 16, scale = 4)
  private BigDecimal orderTax;

  @Column(name = "o_total", precision = 16, scale = 4)
  private BigDecimal orderTotal;

  @Column(name = "o_discount", precision = 16, scale = 4)
  private BigDecimal orderDiscount;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "e_order_id")
  @OrderBy("entryRow")
  private List<OrderEntry> orderEntries;
}