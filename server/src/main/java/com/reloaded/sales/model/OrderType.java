package com.reloaded.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "order_type")
public class OrderType {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_type_id_gen")
  @SequenceGenerator(name = "order_type_id_gen", sequenceName = "order_type_sequence", allocationSize = 1)
  @Column(name = "a_id", nullable = false)
  private Integer typeId;

  @Column(name = "a_state")
  private OrderTypeState typeState;

  @Column(name = "a_order")
  private Integer typeOrder;

  @Size(max = 100)
  @Column(name = "a_key", length = 100)
  private String typeKey;

  @Column(name = "a_num")
  private Long typeNum;

  @Size(max = 100)
  @Column(name = "a_print", length = 100)
  private String typePrint;

  @Column(name = "a_eval")
  private OrderEval typeEval;

  @Size(max = 7)
  @Column(name = "a_ccp", length = 7)
  private String typeCcp;

  @Column(name = "a_tax_pct", precision = 16, scale = 4)
  private BigDecimal typeTaxPct;

  @Size(max = 100)
  @Column(name = "a_note", length = 100)
  private String typeNote;

}