package com.reloaded.sales.service.impl;

import com.reloaded.sales.dto.ReportDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.reloaded.sales.model.Exchange.getFxKey;

@Slf4j
public abstract class ReportByGroup {

  public static final String CURRENCY = "EUR";

  protected abstract boolean isNewGroup(ReportDto group, ReportDto row);

  protected abstract boolean prepareGroup(ReportDto group, ReportDto row);

  protected abstract boolean prepareRow(ReportDto row, ReportDto group);

  private Map<String, BigDecimal> rateMap;

  public Map<String, BigDecimal> getRateMap() {
    return rateMap;
  }

  public void setRateMap(Map<String, BigDecimal> rateMap) {
    this.rateMap = rateMap;
  }

  public List<ReportDto> group(List<ReportDto> rows) {
    List<ReportDto> result = new ArrayList<>();
    int count = 0;
    String target = CURRENCY;

    ReportDto total = null;
    total = ReportDto.builder()
      .reportId(null)
      .orderType(null)
      .entryRow(0)
      .entryAvailable(BigDecimal.ZERO)
      .entryQuantity(BigDecimal.ZERO)
      .entrySum(BigDecimal.ZERO)
      .entryTax(BigDecimal.ZERO)
      .entryTotal(BigDecimal.ZERO)
      .orderCy(target)
      .build();

    total.setGroup(count % 2 == 1 ? "t odd" : "t even");
    if (prepareTotal(total, null)) {
      result.add(total);
    }

    ReportDto group = null;
    for (ReportDto row : rows) {
      String ccp = row.getOrderCcp();
      BigDecimal rate = row.getOrderRate();
      if ( ccp.startsWith(target)) {
        rate = BigDecimal.ONE;
      }
      else if (!ccp.endsWith(target)) {
        String base = ccp.substring(0, 3);
        String key = getFxKey(base, target);
        rate = rateMap.getOrDefault(key, BigDecimal.ZERO);
        row.setOrderCcp(key);
        row.setOrderRate(rate);
      }

      row.setEntryPrice(row.getEntryPrice().multiply(rate));
      row.setEntrySum(row.getEntrySum().multiply(rate));
      row.setEntryTax(row.getEntryTax().multiply(rate));
      row.setEntryTotal(row.getEntryTotal().multiply(rate));
      row.setOrderCy(target);

      if (group == null || isNewGroup(group, row)) {
        prepareTotal(total, group);
        count++;
        group =  ReportDto.builder()
          .reportId(count)
          .orderRate(row.getOrderRate())
          .entryRow(0)
          .entryQuantity(BigDecimal.ZERO)
          .entrySum(BigDecimal.ZERO)
          .entryTax(BigDecimal.ZERO)
          .entryTotal(BigDecimal.ZERO)
          .orderCcp(row.getOrderCcp())
          .orderCy(target)
          .group(count % 2 == 1 ? "m odd" : "m even")
          .build();

        if (prepareGroup(group, row)) {
          result.add(group);
        }
      }

      prepareTotal(group, row);
      row.setGroup(count % 2 == 1 ? "d odd" : "d even");
      if (prepareRow(row, group)) {
        result.add(row);
      }
    }
    prepareTotal(total, group);

    return result;
  }

  protected boolean prepareTotal(ReportDto total, ReportDto row) {
    if (total != null && row != null) {
      if (total.getReportId() == null) {
        total.setEntryRow(
          total.getEntryRow() + row.getEntryRow()
        );
        total.setEntryAvailable(
          total.getEntryAvailable().add(BigDecimal.ONE)
        );
      }
      else {
        total.setEntryRow(
          total.getEntryRow() + 1
        );
      }
      total.setEntryQuantity(
        total.getEntryQuantity().add(row.getEntryQuantity() != null ? row.getEntryQuantity() : BigDecimal.ZERO)
      );
      total.setEntrySum(
        total.getEntrySum().add(row.getEntrySum() != null ? row.getEntrySum() : BigDecimal.ZERO)
      );
      total.setEntryTax(
        total.getEntryTax().add(row.getEntryTax() != null ? row.getEntryTax() : BigDecimal.ZERO)
      );
      total.setEntryTotal(
        total.getEntryTotal().add(row.getEntryTotal() != null ? row.getEntryTotal() : BigDecimal.ZERO)
      );
      return true;
    }
    return total != null;
  }

}
