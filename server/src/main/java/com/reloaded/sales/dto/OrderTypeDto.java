package com.reloaded.sales.dto;

import com.reloaded.sales.model.OrderType;
import com.reloaded.sales.model.OrderEval;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link OrderType}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTypeDto implements Serializable {
  private Integer typeId;
  @Size(max = 100)
  private Integer typeOrder;
  private String typeKey;
  private Long typeNum;
  @Size(max = 100)
  private String typePrint;
  private OrderEval typeEval;
  @Size(max = 7)
  private String typeCcp;
  @Size(max = 100)
  private String typeNote;
  private BigDecimal typeTaxPct;
}