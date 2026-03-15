/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.repository;

import com.reloaded.sales.dto.ReportDto;
import com.reloaded.sales.dto.filter.ReportFilter;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.impl.BaseCriteriaReportRepository;
import com.reloaded.sales.repository.impl.SelectionMapping;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.reloaded.sales.util.ServiceUtils.*;

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
    joins.put(OrderForm.Fields.orderCustomer, customer);

    // Entry join
    Join<OrderForm, OrderEntry> entry = root.join(OrderForm.Fields.orderEntries, JoinType.LEFT);
    joins.put(OrderForm.Fields.orderEntries, entry);

    // Product join
    Join<OrderEntry, Product> product = entry.join(OrderEntry.Fields.entryProduct, JoinType.LEFT);
    joins.put(OrderEntry.Fields.entryProduct, product);

    // Order type join
    Join<OrderForm, OrderType> orderType = root.join(OrderForm.Fields.orderType, JoinType.LEFT);
    joins.put(OrderForm.Fields.orderType, orderType);
  }

  @Override
  protected List<SelectionMapping> buildSelectionMapping(
    Root<OrderForm> orderForm,
    Map<String, Join<?, ?>> joins
  ) {
    Join<OrderForm, Contact> customer = resolveJoin(joins, OrderForm.Fields.orderCustomer);
    Join<OrderForm, OrderEntry> entry = resolveJoin(joins, OrderForm.Fields.orderEntries);
    Join<OrderEntry, Product> product = resolveJoin(joins, OrderEntry.Fields.entryProduct);
    Join<OrderForm, OrderType> orderType = resolveJoin(joins, OrderForm.Fields.orderType);

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
      select(customer, Contact.Fields.contactCode2, ReportDto.Fields.customerCode2),
      select(product, Product.Fields.productName, ReportDto.Fields.productName),
      select(product, Product.Fields.productNote, ReportDto.Fields.productNote),

      select(entry, OrderEntry.Fields.entryId, ReportDto.Fields.entryId),
      select(entry, OrderEntry.Fields.entryRow, ReportDto.Fields.entryRow),
      select(entry, OrderEntry.Fields.entryCode, ReportDto.Fields.entryCode),
      select(entry, OrderEntry.Fields.entryBarcode, ReportDto.Fields.entryBarcode),
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

  protected List<Predicate> buildPredicates(
    CriteriaBuilder cb,
    Root<OrderForm> root,
    Map<String, Join<?, ?>> joins,
    ReportFilter filter
  ) {
    List<Predicate> predicates = new ArrayList<>();

    // ----- order state -----
    (filter.getOrderState() != null
        ? eq(cb, root, filter.getOrderState(), r -> r.get(OrderForm.Fields.orderState))
        : ge(cb, root, OrderState.archived, r -> r.get(OrderForm.Fields.orderState))
    ).ifPresent(predicates::add);

    // ----- customer filters -----
    (anyLike(cb, root, orBlank(filter.getCustomerName()),
      r -> resolveJoin(joins, OrderForm.Fields.orderCustomer).get(Contact.Fields.contactName)
    )).ifPresent(predicates::add);

    (anyLike(cb, root, orBlank(filter.getCustomerLocation()),
      r -> resolveJoin(joins, OrderForm.Fields.orderCustomer).get(Contact.Fields.contactLocation)
    )).ifPresent(predicates::add);

    // ----- product filters -----
    (anyLike(cb, root, orBlank(filter.getProductText()),
      r -> resolveJoin(joins, OrderEntry.Fields.entryProduct).get(Product.Fields.productName),
      r -> resolveJoin(joins, OrderEntry.Fields.entryProduct).get(Product.Fields.productCode),
      r -> resolveJoin(joins, OrderEntry.Fields.entryProduct).get(Product.Fields.productBarcode)
    )).ifPresent(predicates::add);

    (anyLike(cb, root, filter.getProductNote(),
      r -> resolveJoin(joins, OrderEntry.Fields.entryProduct).get(Product.Fields.productNote)
    )).ifPresent(predicates::add);

    // ----- order type -----
    if (filter.getOrderTypeId() != null) {
      Expression<Integer> orderCounter = root.get(OrderForm.Fields.orderCounter);
      Expression<Integer> typeIdExpr = resolveJoin(joins, OrderForm.Fields.orderType)
        .get(OrderType.Fields.typeId);

      predicates.add(
        cb.or(
          cb.equal(orderCounter, filter.getOrderTypeId()),
          cb.equal(typeIdExpr, filter.getOrderTypeId())
        )
      );
    }

    // ----- date range -----
    (between(cb, root, filter.getFromDate(), filter.getToDate(),
      r -> r.get(OrderForm.Fields.orderDate)
    )).ifPresent(predicates::add);

    // ----- order payment -----
    (eq(cb, root, orBlank(filter.getOrderPayment()),
      r -> r.get(OrderForm.Fields.orderPayment)
    )).ifPresent(predicates::add);

    // ----- order number OR order reference -----
    if (orBlank(filter.getOrderNum()) != null) {
      String value = filter.getOrderNum();
      Predicate orderRefLike = cb.like(
        cb.lower(root.get(OrderForm.Fields.orderRef)),
        "%" + value.toLowerCase() + "%"
      );

      Predicate orderNoteLike = cb.like(
        cb.lower(root.get(OrderForm.Fields.orderNote)),
        "%" + value.toLowerCase() + "%"
      );

      try {
        Predicate orderNumEq = cb.equal(root.get(OrderForm.Fields.orderNum), Long.parseLong(value));

        predicates.add(cb.or(orderNumEq, orderRefLike, orderNoteLike));
      }
      catch (NumberFormatException ignored) {
        predicates.add(cb.or(orderRefLike, orderNoteLike));
      }
    }

    return predicates;
  }
  
}


