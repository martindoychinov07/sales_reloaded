package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.ReportFilter;
import com.reloaded.sales.dto.ReportDto;
import com.reloaded.sales.model.Exchange;
import com.reloaded.sales.repository.ExchangeRepository;
import com.reloaded.sales.repository.ReportRepository;
import com.reloaded.sales.service.impl.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.reloaded.sales.model.Exchange.getFxKey;
import static com.reloaded.sales.util.ServiceUtils.orElse;

@Service
@Transactional(readOnly = true)
public class ReportService {

  private final ReportRepository reportRepository;
  private final ExchangeRepository exchangeRepository;

  public ReportService(ReportRepository reportRepository, ExchangeRepository exchangeRepository) {
    this.reportRepository = reportRepository;
    this.exchangeRepository = exchangeRepository;
  }

  @Transactional(readOnly = true)
  public Page<ReportDto> findReport(ReportFilter reportFilter) {
    ReportByGroup reportBy = null;
    String sort = reportFilter.getSort();
    switch (sort) {
      case ReportDto.Fields.orderDate:
        reportBy = new ReportByOrder();
        break;
      case ReportDto.Fields.customerName:
        reportBy = new ReportByCustomer();
        break;
      case ReportDto.Fields.productName:
        reportBy = new ReportByProduct();
        break;
      case ReportDto.Fields.orderPayment:
        reportBy = new ReportByPayment();
        break;
      case ReportDto.Fields.entryRow:
        reportBy = new ReportByRow();
        sort = null;
        break;
      default:
        break;
    }

    PageRequest paging = PageRequest.of(
      orElse(reportFilter.getPage(), 0),
      orElse(reportFilter.getSize(), 1000),
      Strings.isNotBlank(sort)
        ? Sort.by(
        Sort.by(orElse(reportFilter.getDirection(), Sort.Direction.ASC), sort).getOrderFor(sort),
        Sort.Order.asc(ReportDto.Fields.orderDate),
        Sort.Order.asc(ReportDto.Fields.orderId),
        Sort.Order.asc(ReportDto.Fields.entryRow)
      )
      : Sort.by(
          Sort.Order.asc(ReportDto.Fields.orderDate),
          Sort.Order.asc(ReportDto.Fields.orderId),
          Sort.Order.asc(ReportDto.Fields.entryRow)
      )
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
              getFxKey(e.getExchangeBase(), e.getExchangeTarget()),
              e.getExchangeRate()
            ),
            Map.entry(
              getFxKey(e.getExchangeTarget(), e.getExchangeBase()),
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
