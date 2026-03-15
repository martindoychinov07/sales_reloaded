/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.OrderNumDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.ContactRepository;
import com.reloaded.sales.repository.OrderFormRepository;
import com.reloaded.sales.repository.OrderTypeRepository;
import com.reloaded.sales.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderFormService {

  private static final Logger auditLog = LoggerFactory.getLogger("audit");

  private final OrderFormRepository orderFormRepository;
  private final OrderTypeRepository orderTypeRepository;
  private final ContactRepository contactRepository;
  private final ProductRepository productRepository;

  private static void audit(String action, OrderForm res) {
    auditLog.info("[{}:{}] S:{} T:{} N:{} D:{} U:{}", action, res.getOrderId(), res.getOrderState(), res.getOrderType().getTypeId(), res.getOrderNum(), res.getOrderDate(), res.getUpdatedBy());
  }

  private void updateOrderCustomer(OrderForm orderForm) {
    Contact customer;
    List<Contact> list = contactRepository.findAllByContactNameAndContactAddressAndContactCode1AndContactCode2OrderByContactIdDesc(
      orderForm.getOrderCustomer().getContactName(),
      orderForm.getOrderCustomer().getContactAddress(),
      orderForm.getOrderCustomer().getContactCode1(),
      orderForm.getOrderCustomer().getContactCode2()
    );
    if (list.isEmpty()) {
      customer = contactRepository.save(orderForm.getOrderCustomer());
    }
    else {
      String location = orderForm.getOrderCustomer().getContactLocation();
      if (Strings.isBlank(location)) {
        Optional<Contact> found = list
          .stream()
          .filter(contact -> Strings.isBlank(contact.getContactLocation()))
          .findFirst();

        if (found.isEmpty()) {
          customer = contactRepository.save(orderForm.getOrderCustomer());
        }
        else {
          customer = found.get();
        }
      }
      else {
        Optional<Contact> found = list
          .stream()
          .filter(contact -> Objects.equals(contact.getContactLocation(), location))
          .findFirst();

        if (found.isEmpty()) {
          customer = contactRepository.save(orderForm.getOrderCustomer());
        }
        else {
          customer = found.get();
        }
      }
    }

    orderForm.setOrderCustomer(customer);
  }

  private void evaluateOrderEntries(OrderForm orderForm) {
    List<OrderEntry> entries = orderForm.getOrderEntries();
    if (entries == null || entries.isEmpty()) {
      throw new NotFound("orderEntries");
    }

    BigDecimal orderDiscount = BigDecimal.ZERO;
    BigDecimal orderSum = BigDecimal.ZERO;
    BigDecimal orderTax = BigDecimal.ZERO;
    BigDecimal orderTotal = BigDecimal.ZERO;

    BigDecimal taxPct = nvl(orderForm.getOrderTaxPct());

    for (int i = 0; i < entries.size(); i++) {
      OrderEntry entry = entries.get(i);
      entry.setEntryRow(i + 1);
      if (orderForm.getOrderId() == null) {
        entry.setEntryId(null);
      }
      entry.setEntryProduct(productRepository.getReferenceById(entry.getEntryProduct().getProductId()));

      BigDecimal qty = nvl(entry.getEntryQuantity());
      BigDecimal price = nvl(entry.getEntryPrice());
      BigDecimal discountPct = nvl(entry.getEntryDiscountPct());

      // normal = qty * price
      BigDecimal normal = qty.multiply(price);

      // discount = normal * (pct / 100)
      BigDecimal discount = normal
        .multiply(discountPct)
        .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

      // sum = normal - discount
      BigDecimal sum = normal.subtract(discount);

      // tax = sum * taxPct / 100
      BigDecimal tax = sum
        .multiply(taxPct)
        .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

      BigDecimal total = sum.add(tax);

      entry.setEntryDiscount(scale(discount));
      entry.setEntrySum(scale(sum));
      entry.setEntryTax(scale(tax));
      entry.setEntryTotal(scale(total));

      orderDiscount = orderDiscount.add(discount);
      orderSum = orderSum.add(sum);
      orderTax = orderTax.add(tax);
      orderTotal = orderTotal.add(total);
    }

    orderForm.setOrderDiscount(scale(orderDiscount));
    orderForm.setOrderSum(scale(orderSum));
    orderForm.setOrderTax(scale(orderTax));
    orderForm.setOrderTotal(scale(orderTotal));
  }

  private BigDecimal nvl(BigDecimal v) {
    return v == null ? BigDecimal.ZERO : v;
  }

  private BigDecimal scale(BigDecimal v) {
    return v.setScale(2, RoundingMode.HALF_UP);
  }

  private void updateAvailability(OrderEval typeEval, Map<Integer, BigDecimal> availabilityUpdate) {
    List<Integer> ids = availabilityUpdate.keySet().stream().toList();
    if (!ids.isEmpty()) {
      List<Product> productForUpdate = productRepository.findAllProductForUpdate(ids);

      if (typeEval != OrderEval.none) {
        for (Product product: productForUpdate) {
          BigDecimal quantity = availabilityUpdate.get(product.getProductId());
          BigDecimal available = product.getProductAvailable();
          if (typeEval == OrderEval.push) {
            quantity = available.add(quantity);
          }
          else if (typeEval == OrderEval.pull) {
            quantity = available.subtract(quantity);
          }
          auditLog.info("[Q:{}] {} {} {}", product.getProductId(), typeEval, available, quantity);
          product.setProductAvailable(quantity);
        }

        productRepository.saveAll(productForUpdate);
      }
    }
  }

  private static @NonNull Map<Integer, BigDecimal> getQuantities(List<OrderEntry> orderEntries) {
    Map<Integer, BigDecimal> map = orderEntries.stream().collect(Collectors.groupingBy(
      entry -> entry.getEntryProduct().getProductId(),
      Collectors.reducing(
        BigDecimal.ZERO,
        OrderEntry::getEntryQuantity,
        BigDecimal::add
      )
    ));
    return map;
  }

  public OrderForm createOrder(OrderForm orderForm) {
    orderForm.setOrderId(null);
    updateOrderCustomer(orderForm);
    boolean archived = orderForm.getOrderState() == OrderState.archived;
    boolean scheduled = orderForm.getOrderState() == OrderState.scheduled;
    if (!archived && !scheduled) {
      updateOrderNum(orderForm, +1);
    }
    if (orderForm.getOrderState() == OrderState.draft) {
      orderForm.setOrderState(OrderState.finished);
    }
    evaluateOrderEntries(orderForm);

    if (!archived) {
      updateAvailability(orderForm.getOrderType().getTypeEval(), getQuantities(orderForm.getOrderEntries()));
    }

    OrderForm res = orderFormRepository.save(orderForm);
    audit("C", res);
    return res;
  }

  public OrderForm updateOrder(OrderForm changes) {
    OrderForm orderForm = orderFormRepository
      .findById(changes.getOrderId())
      .orElseThrow(() -> new NotFound("Order form not found"));

    OrderState orderState = orderForm.getOrderState();
    if (changes.getOrderState() == OrderState.canceled) {
      throw new RuntimeException("cannot update canceled order");
    }

    boolean archiving = changes.getOrderState() == OrderState.archived;
    boolean scheduling = changes.getOrderState() == OrderState.scheduled;
    if (archiving || scheduling) {

    }
    else if (orderState == OrderState.canceled) {
      throw new RuntimeException("order is already canceled");
    }

    audit("E", orderForm);

    OrderEval oldEval = archiving ? OrderEval.none : orderForm.getOrderType().getTypeEval();

    if (scheduling || archiving) {
      orderForm.setOrderNum(changes.getOrderNum());
      orderForm.setOrderDate(changes.getOrderDate());
    }
    else if (!orderForm.getOrderCounter().equals(changes.getOrderCounter())) {
      updateOrderNum(orderForm, -1);
      orderForm.setOrderCounter(changes.getOrderCounter());
      orderForm.setOrderDate(changes.getOrderDate());
      updateOrderNum(orderForm, +1);
    }

    BeanUtils.copyProperties(changes, orderForm, "orderNum", "orderDate", "orderEntries");
    updateOrderCustomer(orderForm);
    evaluateOrderEntries(changes);

    List<OrderEntry> existingEntries = orderForm.getOrderEntries();

    if (changes.getOrderState() == OrderState.canceled) {
      if (oldEval == OrderEval.pull) {
        updateAvailability(OrderEval.push, getQuantities(orderForm.getOrderEntries()));
      }
      else if (oldEval == OrderEval.push) {
        updateAvailability(OrderEval.pull, getQuantities(orderForm.getOrderEntries()));
      }
    }
    else {
      // Map changed entries by id
      Map<Integer, OrderEntry> changeMap = changes.getOrderEntries().stream()
        .filter(e -> e.getEntryId() != null)
        .collect(Collectors.toMap(OrderEntry::getEntryId, Function.identity()));

      List<OrderEntry> removed = new ArrayList<>();
      for (OrderEntry existing : existingEntries) {
        OrderEntry changed = changeMap.get(existing.getEntryId());

        // no changed entry -> removed
        if (changed == null) {
          removed.add(existing);
          continue;
        }

        // changed entry exists but product is different
        if (!Objects.equals(existing.getEntryProduct().getProductId(), changed.getEntryProduct().getProductId())) {
          throw new RuntimeException("product cannot be changed in existing row");
        }
      }

      // Add new entries (id == null)
      List<OrderEntry> newEntries = changes.getOrderEntries().stream()
        .filter(e -> e.getEntryId() == null)
        .toList();

      List<OrderEntry> initOrderEntry = new ArrayList<>();
      List<OrderEntry> pushOrderEntry = new ArrayList<>();
      List<OrderEntry> pullOrderEntry = new ArrayList<>();

      if (oldEval == OrderEval.pull) {
        pushOrderEntry.addAll(removed);
      }
      else if (oldEval == OrderEval.push) {
        pullOrderEntry.addAll(removed);
      }
      else if (oldEval == OrderEval.init) {
        removed.forEach(row -> row.setEntryQuantity(row.getEntryAvailable()));
        initOrderEntry.addAll(removed);
      }

      existingEntries.removeAll(removed);
      removed.forEach(entry -> entry.setEntryOrder(null));

      // Update existing entries
      OrderEval newEval = archiving ? OrderEval.none : changes.getOrderType().getTypeEval();
      for (OrderEntry entry : existingEntries) {
        OrderEntry change = changeMap.get(entry.getEntryId());
        if (!change.getEntryQuantity().equals(entry.getEntryQuantity())) {
          if (oldEval == OrderEval.pull) {
            pushOrderEntry.add(entry);
          }
          else if (oldEval == OrderEval.push) {
            pullOrderEntry.add(entry);
          }

          if (newEval == OrderEval.pull) {
            pullOrderEntry.add(change);
          }
          else if (newEval == OrderEval.push) {
            pushOrderEntry.add(change);
          }
          else if (newEval == OrderEval.init) {
            initOrderEntry.add(change);
          }
        }
      }

      if (newEval == OrderEval.pull) {
        pullOrderEntry.addAll(newEntries);
      }
      else if (newEval == OrderEval.push) {
        pushOrderEntry.addAll(newEntries);
      }
      else if (newEval == OrderEval.init) {
        initOrderEntry.addAll(newEntries);
      }
      existingEntries.addAll(newEntries);

      updateAvailability(OrderEval.push, getQuantities(pushOrderEntry));
      updateAvailability(OrderEval.pull, getQuantities(pullOrderEntry));
      updateAvailability(OrderEval.init, getQuantities(initOrderEntry));

      // apply changes
      for (OrderEntry entry : existingEntries) {
        OrderEntry change = changeMap.get(entry.getEntryId());
        if (change != null) {
          BeanUtils.copyProperties(change, entry, "entryId", "entryOrder");
        }
        if (!archiving) {
          entry.setEntryAvailable(entry.getEntryProduct().getProductAvailable());
        }
      }
    }

    OrderForm res = orderFormRepository.save(orderForm);
    res.getOrderEntries().sort(Comparator.comparing(OrderEntry::getEntryRow));

    audit("U", res);
    return res;
  }

  public void deleteOrder(Integer id) {
    OrderForm orderForm = orderFormRepository
      .findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));

    if (orderForm.getOrderState() == OrderState.canceled) {
      throw new RuntimeException("order is already canceled");
    }

    updateOrderNum(orderForm, -1);

    OrderEval eval = orderForm.getOrderType().getTypeEval();
    if (eval == OrderEval.pull) {
      updateAvailability(OrderEval.push, getQuantities(orderForm.getOrderEntries()));
    }
    else if (eval == OrderEval.push) {
      updateAvailability(OrderEval.pull, getQuantities(orderForm.getOrderEntries()));
    }

    orderForm.setOrderState(OrderState.canceled);
    OrderForm res = orderFormRepository.save(orderForm);
    audit("D", res);
  }

  @Transactional(readOnly = true)
  public OrderForm getOrderById(Integer id) {
    return orderFormRepository.findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));
  }

  @Transactional(readOnly = true)
  public OrderForm getOrderCopyById(List<Integer> ids, boolean content) {
    OrderForm res = null;
    List<OrderEntry> newEntries = new ArrayList<>();
    List<OrderForm> list = orderFormRepository.findByOrderIdInOrderByOrderDateAsc(ids);
    int row = 1;
    for (OrderForm found: list) {
      if (res == null) {
        res = new OrderForm();
        BeanUtils.copyProperties(
          found,
          res,
          OrderForm.Fields.orderId,
          OrderForm.Fields.orderEntries
        );
      }

      if (content) {
        List<OrderEntry> entries = found.getOrderEntries();
        for (int i = 0; i < entries.size(); i++) {
          OrderEntry entry = entries.get(i);
          OrderEntry newEntry = new OrderEntry();
          BeanUtils.copyProperties(
            entry,
            newEntry,
            OrderEntry.Fields.entryId,
            OrderEntry.Fields.entryOrder
          );
          newEntry.setEntryRow(row++);
          newEntry.setEntryAvailable(newEntry.getEntryProduct().getProductAvailable());
          newEntries.add(newEntry);
        }
      }
    }

    if (res == null) throw new NotFound("OrderForm " + ids);

    res.setOrderEntries(newEntries);
    res.setOrderState(OrderState.draft);
    return res;
  }

  private void updateOrderNum(OrderForm orderForm, int step) {
    Integer counter = orderForm.getOrderCounter();
    OrderType orderType = orderTypeRepository.getOrderTypeForUpdate(counter);

    Long num = orderType.getTypeNum();
    OrderNumDto orderNum;
    if (step == +1) {
      do {
        orderNum = orderFormRepository
          .findOrderNum(num, counter, PageRequest.of(0, 1))
          .stream().findFirst().orElse(null);

        if (orderNum != null) {
          num = num + 1;
        }
      }
      while (orderNum != null);

      orderForm.setOrderNum(num);
      orderType.setTypeNum(num + 1);
      orderTypeRepository.save(orderType);
      auditLog.info("COUNTER " + orderType.getTypeId() + " [+] " + orderType.getTypeNum());
    }
    else if (step == -1) {
      if (orderForm.getOrderNum() < num) {
        num = orderForm.getOrderNum();
        while (num > 1) {
          num = num - 1;
          orderNum = orderFormRepository
            .findOrderNum(num, counter, PageRequest.of(0, 1))
            .stream().findFirst().orElse(null);
          if (orderNum != null) {
            num = num + 1;
            break;
          }
        }

        if (orderType.getTypeNum() != num) {
          orderType.setTypeNum(num);
          orderTypeRepository.save(orderType);
          auditLog.info("COUNTER " + orderType.getTypeId() + " [-] " + num);
        }
      }
    }
  }

  @Transactional(readOnly = true)
  public Optional<OrderForm> getLastOrderByOrderType(Integer orderTypeId, Integer orderId) {
    OrderForm orderForm;
    if (orderId != null) {
      orderForm = orderFormRepository.findById(orderId).orElse(null);
      if (orderForm != null && orderForm.getOrderType().getTypeId().equals(orderTypeId)) {
        return Optional.of(orderForm);
      }
    }

    Optional<OrderForm> last = orderFormRepository
      .findLastActiveForType(orderTypeId, PageRequest.of(0, 1))
      .stream().findFirst();

    OrderType orderType;
    if (last.isPresent()) {
      orderForm = last.get();
      orderType = orderForm.getOrderType();
    }
    else {
      orderForm = new OrderForm();
      orderType = orderTypeRepository.findById(orderTypeId).get();
      orderForm.setOrderType(orderType);
    }

    orderForm.setOrderCounter(orderType.getTypeCounter());
    orderForm.setOrderDate(OffsetDateTime.now());
    orderForm.setOrderState(OrderState.draft);
    if (!orderType.getTypeId().equals(orderType.getTypeCounter())) {
      orderType = orderTypeRepository.findById(orderType.getTypeCounter()).get();
    }
    orderForm.setOrderNum(orderType.getTypeNum());

    return Optional.of(orderForm);
  }

}
