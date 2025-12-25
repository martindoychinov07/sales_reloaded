package com.reloaded.sales.dto;

import com.reloaded.sales.model.OrderState;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface OrderFormView {
  Integer getOrderId();

  OrderState getOrderState();

  OffsetDateTime getOrderDate();

  Long getOrderNum();

  String getOrderTypeTypeKey();

  String getOrderSupplierContactName();

  String getOrderCustomerContactName();

  String getOrderCcp();

  BigDecimal getOrderRate();

  BigDecimal getOrderSum();

  BigDecimal getOrderTax();

  BigDecimal getOrderTotal();
}
