package com.reloaded.sales.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for {@link com.reloaded.sales.model.Product}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
  private Integer productId;
  private Integer productVersion;
  private Integer productRefId;
  @Size(max = 50)
  private String productBarcode;
  @Size(max = 200)
  private String productName;
  @Size(max = 200)
  private String productNote;
  private Integer productUnits;
  @Size(max = 30)
  private String productMeasure;
  private BigDecimal productAvailable;
  @Size(max = 50)
  private String productCode;
  @Size(max = 50)
  private String productCode1;
  @Size(max = 50)
  private String productCode2;
  @Size(max = 50)
  private String productCode3;
  @Size(max = 50)
  private String productCode4;
  @Size(max = 50)
  private String productCode5;
  @Size(max = 50)
  private String productCode6;
  @Size(max = 50)
  private String productCode7;
  @Size(max = 50)
  private String productCode8;
  @Size(max = 50)
  private String productCode9;
  private String productCy;
  private BigDecimal productPrice;
  private BigDecimal productPrice1;
  private BigDecimal productPrice2;
  private BigDecimal productPrice3;
  private BigDecimal productPrice4;
  private BigDecimal productPrice5;
  private BigDecimal productPrice6;
  private BigDecimal productPrice7;
  private BigDecimal productPrice8;
  private BigDecimal productPrice9;
}