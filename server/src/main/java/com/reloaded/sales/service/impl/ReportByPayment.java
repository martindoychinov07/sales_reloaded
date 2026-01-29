/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;

import java.util.Objects;

public class ReportByPayment extends ReportByGroup {

  @Override
  protected boolean isNewGroup(ReportDto group, ReportDto row) {
    return !Objects.equals(group.getOrderPayment(), row.getOrderPayment());
  }

  @Override
  protected boolean prepareGroup(ReportDto group, ReportDto row) {
    group.setOrderPayment(row.getOrderPayment());
    return true;
  }

  @Override
  protected boolean prepareRow(ReportDto row, ReportDto group) {
    row.setOrderPayment(null);
    return true;
  }

}