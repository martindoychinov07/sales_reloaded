/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;

import java.util.Objects;

public class ReportByLocation extends ReportByGroup {

  @Override
  protected boolean isNewGroup(ReportDto group, ReportDto row) {
    return !Objects.equals(group.getCustomerName(), row.getCustomerName())
      || !Objects.equals(group.getCustomerCode2(), row.getCustomerCode2())
      || !Objects.equals(group.getCustomerLocation(), row.getCustomerLocation());
  }

  @Override
  protected boolean prepareGroup(ReportDto group, ReportDto row) {
    group.setCustomerName(row.getCustomerName());
    group.setCustomerLocation(row.getCustomerLocation());
    group.setCustomerCode2(row.getCustomerCode2());
    return true;
  }

  @Override
  protected boolean prepareRow(ReportDto row, ReportDto group) {
    row.setCustomerName(null);
    row.setCustomerLocation(null);
    row.setCustomerCode2(null);
    return true;
  }

}
