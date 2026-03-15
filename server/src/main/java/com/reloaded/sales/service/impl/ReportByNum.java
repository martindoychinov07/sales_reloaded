/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;

import java.util.Objects;

public class ReportByNum extends ReportByGroup {

  @Override
  protected boolean isNewGroup(ReportDto group, ReportDto row) {
    return !Objects.equals(group.getOrderId(), row.getOrderId());
  }

  @Override
  protected boolean prepareGroup(ReportDto group, ReportDto row) {
    group.setOrderId(row.getOrderId());
    group.setOrderDate(row.getOrderDate());
    group.setOrderType(row.getOrderType());
    group.setOrderNum(row.getOrderNum());
    group.setOrderState(row.getOrderState());
    group.setCustomerName(row.getCustomerName());
    group.setCustomerLocation(row.getCustomerLocation());
    group.setCustomerCode2(row.getCustomerCode2());
    group.setOrderResp(row.getOrderResp());
    group.setOrderPayment(row.getOrderPayment());
    group.setOrderPaymentDate(row.getOrderPaymentDate());
    return true;
  }

  @Override
  protected boolean prepareRow(ReportDto row, ReportDto group) {
    return false;
  }

}
