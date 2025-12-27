package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.ContactRepository;
import com.reloaded.sales.repository.OrderFormRepository;
import com.reloaded.sales.repository.OrderTypeRepository;
import com.reloaded.sales.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class OrderFormService {
  private final OrderFormRepository orderFormRepository;
  private final OrderTypeRepository orderTypeRepository;
  private final ContactRepository contactRepository;
  private final ProductRepository productRepository;

  @PersistenceContext
  private EntityManager em;

  /**
   *
   * @param orderFormRepository
   * @param orderTypeRepository
   * @param contactRepository
   * @param productRepository
   */
  OrderFormService(
    OrderFormRepository orderFormRepository,
    OrderTypeRepository orderTypeRepository,
    ContactRepository contactRepository,
    ProductRepository productRepository
  ) {
    this.orderFormRepository = orderFormRepository;
    this.orderTypeRepository = orderTypeRepository;
    this.contactRepository = contactRepository;
    this.productRepository = productRepository;
  }

  /**
   *
   * @param orderForm
   */
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
      if (location.isBlank()) {
        Optional<Contact> found = list
          .stream()
          .filter(contact -> contact.getContactLocation() == null || contact.getContactLocation().isBlank())
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

  /**
   *
   * @param orderForm
   */
  private void updateOrderNum(OrderForm orderForm) {
    OrderType orderType = orderTypeRepository.getOrderTypeForUpdate(orderForm.getOrderType().getTypeId());
    orderForm.setOrderType(orderType);
    orderForm.setOrderNum(orderType.getTypeNum());

    orderType.setTypeNum(orderType.getTypeNum() + 1);
    orderTypeRepository.save(orderType);
  }

  /**
   *
   * @param orderForm
   */
  private void evaluateOrderEntries(OrderForm orderForm) {
    List<OrderEntry> entries = orderForm.getOrderEntries();
    for (int i = 0; i < entries.size(); i++) {
      OrderEntry entry = entries.get(i);
      entry.setEntryRow(i + 1);
      entry.setEntryProduct(productRepository.getReferenceById(entry.getEntryProduct().getProductId()));
    }
  }

  /**
   *
   * @param typeEval
   * @param availabilityUpdate
   */
  private void updateAvailability(OrderEval typeEval, Map<Integer, Integer> availabilityUpdate) {
    List<Integer> ids = availabilityUpdate.keySet().stream().toList();
    if (!ids.isEmpty()) {
      List<Product> productForUpdate = productRepository.findAllProductForUpdate(ids);

      if (typeEval != OrderEval.none) {
        for (Product product: productForUpdate) {
          Integer quantity = availabilityUpdate.get(product.getProductId());
          if (typeEval == OrderEval.push) {
            quantity = product.getProductAvailable() + quantity;
          }
          else if (typeEval == OrderEval.pull) {
            quantity = product.getProductAvailable() - quantity;
          }
          log.info("updateAvailability: {} {} {} {}", typeEval, product.getProductId(), availabilityUpdate.get(product.getProductId()), quantity);

          product.setProductAvailable(quantity);
        }

        productRepository.saveAll(productForUpdate);
      }
    }
  }

  /**
   *
   * @param orderEntries
   * @return
   */
  private static @NonNull Map<Integer, Integer> getQuantities(List<OrderEntry> orderEntries) {
    Map<Integer, Integer> map = orderEntries.stream().collect(Collectors.groupingBy(
      entry -> entry.getEntryProduct().getProductId(),
      Collectors.summingInt(OrderEntry::getEntryQuantity)
    ));
    return map;
  }

  /**
   *
   * @param orderForm
   * @return
   */
  public OrderForm createOrder(OrderForm orderForm) {
    updateOrderCustomer(orderForm);
    updateOrderNum(orderForm);
    if (orderForm.getOrderState() == OrderState.draft) {
      orderForm.setOrderState(OrderState.finished);
    }
    evaluateOrderEntries(orderForm);
    updateAvailability(orderForm.getOrderType().getTypeEval(), getQuantities(orderForm.getOrderEntries()));

    return orderFormRepository.save(orderForm);
  }

  /**
   *
   * @param changes
   * @return
   */
  public OrderForm updateOrder(OrderForm changes) {
    OrderForm orderForm = orderFormRepository
      .findById(changes.getOrderId())
      .orElseThrow(() -> new NotFound("Order form not found"));

    if (!orderForm.getOrderType().getTypeId().equals(changes.getOrderType().getTypeId())) {
      updateOrderNum(orderForm);
    }

    OrderEval oldEval = orderForm.getOrderType().getTypeEval();
    BeanUtils.copyProperties(changes, orderForm, "orderEntries");

    updateOrderCustomer(orderForm);
    evaluateOrderEntries(orderForm);

    List<OrderEntry> existingEntries = orderForm.getOrderEntries();

    // Map changed entries by id
    Map<Integer, OrderEntry> changeMap = changes.getOrderEntries().stream()
      .filter(e -> e.getEntryId() != null)
      .collect(Collectors.toMap(OrderEntry::getEntryId, Function.identity()));


    List<OrderEntry> initOrderEntry = new ArrayList<>();
    List<OrderEntry> pushOrderEntry = new ArrayList<>();
    List<OrderEntry> pullOrderEntry = new ArrayList<>();

    // Remove entries that are no longer present
    List<OrderEntry> removed = existingEntries.stream().filter(entry -> !changeMap.containsKey(entry.getEntryId())).toList();
    if (oldEval == OrderEval.pull) {
      pushOrderEntry.addAll(removed);
    }
    else if (oldEval == OrderEval.push) {
      pullOrderEntry.addAll(removed);
    }
    existingEntries.removeAll(removed);

    // Update existing entries
    OrderEval newEval = changes.getOrderType().getTypeEval();
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

    // Add new entries (id == null)
    List<OrderEntry> newEntries = changes.getOrderEntries().stream()
      .filter(e -> e.getEntryId() == null)
      .toList();

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
      BeanUtils.copyProperties(change, entry, "entryId", "entryOrder");
    }

    return orderFormRepository.save(orderForm);
  }

  /**
   *
   * @param id
   */
  public void deleteOrder(Integer id) {
    OrderForm orderForm = orderFormRepository
      .findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));

    orderForm.setOrderState(OrderState.canceled);
    orderFormRepository.save(orderForm);
  }

  /**
   *
   * @param id
   * @return
   */
  public OrderForm getOrderById(Integer id) {
    return orderFormRepository.findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));
  }

  /**
   *
   * @param id
   * @return
   */
  public OrderForm getOrderCopyById(Integer id, boolean content) {
    OrderForm res = new OrderForm();
    OrderForm found = orderFormRepository.findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));

    BeanUtils.copyProperties(
      found,
      res,
      OrderForm.Fields.orderId
    );

    if (content) {
      List<OrderEntry> entries = res.getOrderEntries();
      for (int i = 0; i < entries.size(); i++) {
        OrderEntry entry = entries.get(i);
        entry.setEntryRow(i + 1);
        entry.setEntryProduct(productRepository.findById(entry.getEntryProduct().getProductId()).orElseThrow());
      }
    }
    else {
      res.setOrderEntries(new ArrayList());
    }

    res.setOrderState(OrderState.draft);
    return res;
  }

  /**
   *
   * @param orderTypeId
   * @return
   */
  public OrderForm getLastOrderByOrderType(Integer orderTypeId) {
    OrderType orderType = em.getReference(OrderType.class, orderTypeId);
    return orderFormRepository.findTopByOrderTypeOrderByOrderNumDesc(orderType)
      .orElseThrow(() -> new NotFound("Order form not found"));
  }

}
