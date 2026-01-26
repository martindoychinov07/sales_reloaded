package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;

import java.util.Objects;

public class ReportByCustomer extends ReportByGroup {

  @Override
  protected boolean isNewGroup(ReportDto group, ReportDto row) {
    return !Objects.equals(group.getCustomerName(), row.getCustomerName());
  }

  @Override
  protected boolean prepareGroup(ReportDto group, ReportDto row) {
    group.setCustomerName(row.getCustomerName());
    return true;
  }

  @Override
  protected boolean prepareRow(ReportDto row, ReportDto group) {
    row.setCustomerName(null);
    return true;
  }

}
