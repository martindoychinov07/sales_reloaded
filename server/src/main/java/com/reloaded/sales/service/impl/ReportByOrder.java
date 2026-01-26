package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;

import java.util.Objects;

public class ReportByOrder extends ReportByGroup {

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
    group.setOrderResp(row.getOrderResp());
    group.setOrderPayment(row.getOrderPayment());
    group.setOrderPaymentDate(row.getOrderPaymentDate());
    return true;
  }

  @Override
  protected boolean prepareRow(ReportDto row, ReportDto group) {
    row.setOrderDate(null);
    row.setOrderType(null);
    row.setOrderNum(null);
    row.setOrderState(null);
    row.setCustomerName(null);
    row.setOrderCcp(null);
    row.setOrderRate(null);
    row.setOrderResp(null);
    row.setOrderPayment(null);
    row.setOrderPaymentDate(null);
    return true;
  }

}
