/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.repository;

import com.reloaded.sales.dto.ReportDto;
import com.reloaded.sales.dto.filter.ReportFilter;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.impl.BaseCriteriaReportRepository;
import com.reloaded.sales.repository.impl.SelectionMapping;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.reloaded.sales.repository.impl.CriteriaUtils.select;

@Slf4j
@Repository
public class ReportRepository
  extends BaseCriteriaReportRepository<OrderForm, ReportFilter, ReportDto> {

  @Override
  protected Class<OrderForm> entityClass() {
    return OrderForm.class;
  }

  @Override
  protected Class<ReportDto> dtoClass() {
    return ReportDto.class;
  }

  @Override
  protected void applyJoins(Root<OrderForm> root, Map<String, Join<?, ?>> joins) {
    // Customer join
    Join<OrderForm, Contact> customer = root.join(OrderForm.Fields.orderCustomer, JoinType.LEFT);
    joins.put("customer", customer);

    // Entry join
    Join<OrderForm, OrderEntry> entry = root.join(OrderForm.Fields.orderEntries, JoinType.LEFT);
    joins.put("entry", entry);

    // Product join
    Join<OrderEntry, Product> product = entry.join(OrderEntry.Fields.entryProduct, JoinType.LEFT);
    joins.put("product", product);

    // Order type join
    Join<OrderForm, OrderType> orderType = root.join(OrderForm.Fields.orderType, JoinType.LEFT);
    joins.put("orderType", orderType);
  }

  @Override
  protected List<SelectionMapping> buildSelectionMapping(
    Root<OrderForm> orderForm,
    Map<String, Join<?, ?>> joins
  ) {
    Join<OrderForm, Contact> customer = resolveJoin(joins, "customer");
    Join<OrderForm, OrderEntry> entry = resolveJoin(joins, "entry");
    Join<OrderEntry, Product> product = resolveJoin(joins, "product");
    Join<OrderForm, OrderType> orderType = resolveJoin(joins, "orderType");

    return List.of(
      select(orderForm, OrderForm.Fields.orderId, ReportDto.Fields.orderId),
      select(orderForm, OrderForm.Fields.orderState, ReportDto.Fields.orderState),
      select(orderForm, OrderForm.Fields.orderDate, ReportDto.Fields.orderDate),
      select(orderForm, OrderForm.Fields.orderNum, ReportDto.Fields.orderNum),
      select(orderForm, OrderForm.Fields.orderCcp, ReportDto.Fields.orderCcp),
      select(orderForm, OrderForm.Fields.orderRate, ReportDto.Fields.orderRate),
      select(orderForm, OrderForm.Fields.orderResp, ReportDto.Fields.orderResp),
      select(orderForm, OrderForm.Fields.orderPayment, ReportDto.Fields.orderPayment),
      select(orderForm, OrderForm.Fields.orderPaymentDate, ReportDto.Fields.orderPaymentDate),

      select(orderType, OrderType.Fields.typeKey, ReportDto.Fields.orderType),
      select(customer, Contact.Fields.contactName, ReportDto.Fields.customerName),
      select(customer, Contact.Fields.contactLocation, ReportDto.Fields.customerLocation),
      select(product, Product.Fields.productName, ReportDto.Fields.productName),

      select(entry, OrderEntry.Fields.entryId, ReportDto.Fields.entryId),
      select(entry, OrderEntry.Fields.entryRow, ReportDto.Fields.entryRow),
      select(entry, OrderEntry.Fields.entryLabel, ReportDto.Fields.entryLabel),
      select(entry, OrderEntry.Fields.entryUnits, ReportDto.Fields.entryUnits),
      select(entry, OrderEntry.Fields.entryMeasure, ReportDto.Fields.entryMeasure),
      select(entry, OrderEntry.Fields.entryAvailable, ReportDto.Fields.entryAvailable),
      select(entry, OrderEntry.Fields.entryQuantity, ReportDto.Fields.entryQuantity),
      select(entry, OrderEntry.Fields.entryPrice, ReportDto.Fields.entryPrice),
      select(entry, OrderEntry.Fields.entryDiscountPct, ReportDto.Fields.entryDiscountPct),
      select(entry, OrderEntry.Fields.entryDiscount, ReportDto.Fields.entryDiscount),
      select(entry, OrderEntry.Fields.entrySum, ReportDto.Fields.entrySum),
      select(entry, OrderEntry.Fields.entryTax, ReportDto.Fields.entryTax),
      select(entry, OrderEntry.Fields.entryTotal, ReportDto.Fields.entryTotal)
    );
  }

  @Override
  protected List<Predicate> buildPredicates(
    CriteriaBuilder cb,
    Root<OrderForm> root,
    Map<String, Join<?, ?>> joins,
    ReportFilter filter
  ) {
    List<Predicate> predicates = new ArrayList<>();

    if (filter.getOrderState() != null) {
      predicates.add(cb.equal(
        root.get(OrderForm.Fields.orderState), filter.getOrderState()
      ));
    }
    else {
      predicates.add(cb.greaterThanOrEqualTo(
        root.get(OrderForm.Fields.orderState), OrderState.archived
      ));
    }

    if (Strings.isNotBlank(filter.getCustomerName())) {
      predicates.add(cb.like(
        cb.lower(resolveJoin(joins, "customer").get(Contact.Fields.contactName)),
        "%" + filter.getCustomerName().toLowerCase() + "%"
      ));
    }

    if (Strings.isNotBlank(filter.getCustomerLocation())) {
      predicates.add(cb.like(
        cb.lower(resolveJoin(joins, "customer").get(Contact.Fields.contactLocation)),
        "%" + filter.getCustomerLocation().toLowerCase() + "%"
      ));
    }

    if (Strings.isNotBlank(filter.getProductName())) {
      predicates.add(cb.like(
        cb.lower(resolveJoin(joins, "product").get(Product.Fields.productName)),
        "%" + filter.getProductName().toLowerCase() + "%"
      ));
    }

    if (filter.getOrderTypeId() != null) {
      if (ReportDto.Fields.entryRow.equals(filter.getSort())) {
        predicates.add(cb.equal(
          resolveJoin(joins, "orderType").get(OrderType.Fields.typeCounter), filter.getOrderTypeId()
        ));
      }
      else {
        predicates.add(cb.equal(
          resolveJoin(joins, "orderType").get(OrderType.Fields.typeId), filter.getOrderTypeId()
        ));
      }
    }

    if (filter.getFromDate() != null) {
      predicates.add(cb.greaterThanOrEqualTo(
        root.get(OrderForm.Fields.orderDate), filter.getFromDate()
      ));
    }

    if (filter.getToDate() != null) {
      predicates.add(cb.lessThanOrEqualTo(
        root.get(OrderForm.Fields.orderDate), filter.getToDate()
      ));
    }

    if (Strings.isNotBlank(filter.getOrderPayment())) {
      predicates.add(cb.equal(
        root.get(OrderForm.Fields.orderPayment), filter.getOrderPayment()
      ));
    }

    if (filter.getOrderNum() != null) {
      predicates.add(cb.equal(
        root.get(OrderForm.Fields.orderNum), filter.getOrderNum()
      ));
    }

    return predicates;
  }
}


