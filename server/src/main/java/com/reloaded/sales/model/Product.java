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
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
  @SequenceGenerator(name = "product_id_gen", sequenceName = "product_sequence", allocationSize = 1)
  @Column(name = "p_id", nullable = false)
  private Integer productId;

  @Version
  @Column(name = "p_version")
  private Integer productVersion;

  @Column(name = "p_ref_id")
  private Integer productRefId;

  @Column(name = "p_state")
  private ProductState productState;

  @Size(max = 50)
  @Column(name = "p_barcode", length = 50)
  private String productBarcode;

  @Size(max = 200)
  @Column(name = "p_name", length = 200)
  private String productName;

  @Size(max = 200)
  @Column(name = "p_note", length = 200)
  private String productNote;

  @Column(name = "p_units")
  private Integer productUnits;

  @Size(max = 30)
  @Column(name = "p_measure", length = 30)
  private String productMeasure;

  @Column(name = "p_available")
  private Integer productAvailable;

  @Size(max = 50)
  @Column(name = "p_code", length = 50)
  private String productCode;

  @Size(max = 50)
  @Column(name = "p_code1", length = 50)
  private String productCode1;

  @Size(max = 50)
  @Column(name = "p_code2", length = 50)
  private String productCode2;

  @Size(max = 50)
  @Column(name = "p_code3", length = 50)
  private String productCode3;

  @Size(max = 50)
  @Column(name = "p_code4", length = 50)
  private String productCode4;

  @Size(max = 50)
  @Column(name = "p_code5", length = 50)
  private String productCode5;

  @Size(max = 50)
  @Column(name = "p_code6", length = 50)
  private String productCode6;

  @Size(max = 50)
  @Column(name = "p_code7", length = 50)
  private String productCode7;

  @Size(max = 50)
  @Column(name = "p_code8", length = 50)
  private String productCode8;

  @Size(max = 50)
  @Column(name = "p_code9", length = 50)
  private String productCode9;

  @Size(max = 50)
  @Column(name = "p_cy", length = 3)
  private String productCy;

  @Column(name = "p_price", precision = 16, scale = 4)
  private BigDecimal productPrice;

  @Column(name = "p_price1", precision = 16, scale = 4)
  private BigDecimal productPrice1;

  @Column(name = "p_price2", precision = 16, scale = 4)
  private BigDecimal productPrice2;

  @Column(name = "p_price3", precision = 16, scale = 4)
  private BigDecimal productPrice3;

  @Column(name = "p_price4", precision = 16, scale = 4)
  private BigDecimal productPrice4;

  @Column(name = "p_price5", precision = 16, scale = 4)
  private BigDecimal productPrice5;

  @Column(name = "p_price6", precision = 16, scale = 4)
  private BigDecimal productPrice6;

  @Column(name = "p_price7", precision = 16, scale = 4)
  private BigDecimal productPrice7;

  @Column(name = "p_price8", precision = 16, scale = 4)
  private BigDecimal productPrice8;

  @Column(name = "p_price9", precision = 16, scale = 4)
  private BigDecimal productPrice9;

}