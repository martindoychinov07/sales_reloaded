package com.reloaded.sales.model;

import com.reloaded.sales.converter.GenericEnumConverter;
import com.reloaded.sales.dto.OrderEntryDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

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
  private Instant orderDate;

  @Size(max = 20)
  @Column(name = "o_book", length = 20)
  private String orderBook;

  @Column(name = "o_num")
  private Long orderNum;

  @Column(name = "o_type")
  private Integer orderType;

  @Size(max = 200)
  @Column(name = "o_view", length = 200)
  private String orderView;

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
  @Column(name = "o_tag_vce", length = 100)
  private String orderTagVce;

  @Size(max = 100)
  @Column(name = "o_tag_rcvd", length = 100)
  private String orderTagRcvd;

  @Size(max = 100)
  @Column(name = "o_tag_dlvd", length = 100)
  private String orderTagDlvd;

  @Size(max = 100)
  @Column(name = "o_tag_ref", length = 100)
  private String orderTagRef;

  @Size(max = 100)
  @Column(name = "o_tag_payment_term", length = 100)
  private String orderTagPaymentTerm;

  @Column(name = "o_availability")
  private Integer orderAvailability;

  @Column(name = "o_rows")
  private Integer orderRows;

  @Column(name = "o_vat", precision = 16, scale = 4)
  private BigDecimal orderVat;

  @Size(max = 7)
  @Column(name = "o_ccy", length = 7)
  private String orderCcy;

  @Column(name = "o_rate", precision = 16, scale = 4)
  private BigDecimal orderRate;

  @Column(name = "o_total", precision = 16, scale = 4)
  private BigDecimal orderTotal;

  @Column(name = "o_total_tax", precision = 16, scale = 4)
  private BigDecimal orderTotalTax;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "e_order_id")
  @OrderBy("entryRow asc")
  private Set<OrderEntry> orderEntries;
}