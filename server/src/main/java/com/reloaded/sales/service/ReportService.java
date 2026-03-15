/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.config.AppConfig;
import com.reloaded.sales.dto.filter.ReportFilter;
import com.reloaded.sales.dto.ReportDto;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.repository.ExchangeRepository;
import com.reloaded.sales.repository.ReportRepository;
import com.reloaded.sales.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.reloaded.sales.model.Exchange.getFxKey;
import static com.reloaded.sales.util.ServiceUtils.orElse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

  private final ReportRepository reportRepository;
  private final ExchangeRepository exchangeRepository;
  private final AppConfig appConfig;

  @Transactional(readOnly = true)
  public Page<ReportDto> findReport(ReportFilter reportFilter) {
    ReportByGroup reportBy = null;
    String sort = reportFilter.getSort();
    switch (sort) {
      case ReportDto.Fields.orderDate:
      case ReportDto.Fields.orderNum:
        reportBy = new ReportByOrder();
        break;
      case ReportDto.Fields.customerName:
        reportBy = new ReportByCustomer();
        break;
      case ReportDto.Fields.customerLocation:
        reportBy = new ReportByLocation();
        sort = ReportDto.Fields.customerName + "," + ReportDto.Fields.customerLocation;
        break;
      case ReportDto.Fields.productName:
        reportBy = new ReportByProduct();
        break;
      case ReportDto.Fields.productNote:
        reportBy = new ReportByNote();
        break;
      case ReportDto.Fields.orderPayment:
        reportBy = new ReportByPayment();
        break;
      case ReportDto.Fields.orderType:
        reportBy = new ReportByNum();
        sort = ReportDto.Fields.orderNum;
        break;
      case ReportDto.Fields.entryRow:
//        reportBy = new ReportByRow();
        sort = null;
        break;
      default:
        break;
    }

    List<Sort.Order> orders = new ArrayList<>();

    if (Strings.isNotBlank(sort)) {

      Sort.Direction direction =
        orElse(reportFilter.getDirection(), Sort.Direction.ASC);

      for (String property : sort.split(",")) {
        orders.add(new Sort.Order(direction, property.trim()));
      }
    }

    /* always add your fixed tie-breaker sorting */
    orders.add(Sort.Order.asc(ReportDto.Fields.orderDate));
    orders.add(Sort.Order.asc(ReportDto.Fields.orderId));
    orders.add(Sort.Order.asc(ReportDto.Fields.entryRow));

    PageRequest paging = PageRequest.of(
      orElse(reportFilter.getPage(), 1) - 1,
      orElse(reportFilter.getSize(), 1000),
      Sort.by(orders)
    );

    Page<ReportDto> res = reportRepository.findAll(reportFilter, paging);
    if (reportBy == null) {
      res.getContent().forEach(row -> row.setReportId(row.getEntryId()));
      return res;
    }
    else {
      List<Exchange> exchanges = exchangeRepository.findAll();
      Map<String, BigDecimal> rateMap =
        exchanges.stream()
          .flatMap(e -> Stream.of(
            Map.entry(
              getFxKey(e.getExchangeTarget(), e.getExchangeSource()),
              e.getExchangeRate()
            ),
            Map.entry(
              getFxKey(e.getExchangeSource(), e.getExchangeTarget()),
              BigDecimal.ONE.divide(
                e.getExchangeRate(), 4, RoundingMode.HALF_UP
              )
            )
          ))
          .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (existing, ignored) -> existing // keep original if already exists
          ));

      reportBy.setCcy(appConfig.getCcy());
      reportBy.setRateMap(rateMap);
      List<ReportDto> list = reportBy.group(res.getContent());

      return new PageImpl<>(
        list,
        paging,
        res.getTotalElements()
      );
    }
  }

}
