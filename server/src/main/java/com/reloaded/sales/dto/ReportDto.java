package com.reloaded.sales.dto;

import com.reloaded.sales.model.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
  private Integer reportId;
  private Integer orderId;
  private Integer entryId;
  private OrderState orderState;
  private OffsetDateTime orderDate;
  private Long orderNum;
  private String orderType;
  private String supplierName;
  private String supplierLocation;
  private String customerName;
  private String customerLocation;
  private String orderCcp;
  private String orderCy;
  private BigDecimal orderRate;
  private String orderResp;
  private String orderPayment;
  private OffsetDateTime orderPaymentDate;
  private String productName;
  private Integer entryRow;
  private String entryLabel;
  private Integer entryUnits;
  private String entryMeasure;
  private BigDecimal entryAvailable;
  private BigDecimal entryQuantity;
  private BigDecimal entryPrice;
  private BigDecimal entryDiscountPct;
  private BigDecimal entryDiscount;
  private BigDecimal entrySum;
  private BigDecimal entryTax;
  private BigDecimal entryTotal;
  private String group;
}
