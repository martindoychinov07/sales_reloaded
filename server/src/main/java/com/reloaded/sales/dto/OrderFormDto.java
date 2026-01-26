package com.reloaded.sales.dto;

import com.reloaded.sales.model.OrderState;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for {@link com.reloaded.sales.model.OrderForm}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDto implements Serializable {
  private Integer orderId;
  private Integer orderVersion;
  private Integer orderRefId;
  private OrderState orderState;
  private OffsetDateTime orderDate;
  private Long orderNum;
  private Integer orderCounter;
  private OrderTypeDto orderType;
  private Integer orderUserId;
  private ContactDto orderSupplier;
  private ContactDto orderCustomer;
  @Size(max = 300)
  private String orderNote;
  @Size(max = 100)
  private String orderResp;
  private OffsetDateTime orderRespDate;
  @Size(max = 100)
  private String orderDlvd;
  private OffsetDateTime orderDlvdDate;
  @Size(max = 100)
  private String orderRcvd;
  private OffsetDateTime orderRcvdDate;
  @Size(max = 100)
  private String orderRef;
  @Size(max = 20)
  private String orderPayment;
  private OffsetDateTime orderPaymentDate;
  private Integer orderEval;
  private OffsetDateTime orderDate1;
  private OffsetDateTime orderDate2;
  @Size(max = 100)
  private String orderText1;
  @Size(max = 100)
  private String orderText2;
  private Integer orderRows;
  private BigDecimal orderTaxPct;
  @Size(max = 7)
  private String orderCcp;
  private BigDecimal orderRate;
  private BigDecimal orderDiscount;
  private BigDecimal orderSum;
  private BigDecimal orderTax;
  private BigDecimal orderTotal;
  private List<OrderEntryDto> orderEntries;
}