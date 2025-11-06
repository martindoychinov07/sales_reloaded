package com.reloaded.sales.dto;

import com.reloaded.sales.model.OrderState;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.reloaded.sales.model.OrderForm}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDto {
  private Integer orderId;
  private Integer orderVersion;
  private Integer orderRefId;
  private OrderState orderState;
  private Instant orderDate;
  @Size(max = 20)
  private String orderBook;
  private Long orderNum;
  private Integer orderType;
  @Size(max = 200)
  private String orderView;
  private Integer orderUserId;
  private ContactDto orderSupplier;
  private ContactDto orderCustomer;
  @Size(max = 300)
  private String orderNote;
  @Size(max = 100)
  private String orderTagVce;
  @Size(max = 100)
  private String orderTagRcvd;
  @Size(max = 100)
  private String orderTagDlvd;
  @Size(max = 100)
  private String orderTagRef;
  @Size(max = 100)
  private String orderTagPaymentTerm;
  private Integer orderAvailability;
  private Integer orderRows;
  private BigDecimal orderVat;
  @Size(max = 7)
  private String orderCcy;
  private BigDecimal orderRate;
  private BigDecimal orderTotal;
  private BigDecimal orderTotalTax;
  private List<OrderEntryDto> orderEntries;
}