package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;

import java.util.Objects;

public class ReportByProduct extends ReportByGroup {

  @Override
  protected boolean isNewGroup(ReportDto group, ReportDto row) {
    return !Objects.equals(group.getProductName(), row.getProductName());
  }

  @Override
  protected boolean prepareGroup(ReportDto group, ReportDto row) {
    group.setProductName(row.getProductName());
    return true;
  }

  @Override
  protected boolean prepareRow(ReportDto row, ReportDto group) {
    row.setProductName(null);
    return true;
  }

}