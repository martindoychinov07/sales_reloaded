package com.reloaded.sales.service;

import com.reloaded.sales.dto.OrderNumDto;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.*;
import com.reloaded.sales.repository.ContactRepository;
import com.reloaded.sales.repository.OrderFormRepository;
import com.reloaded.sales.repository.OrderTypeRepository;
import com.reloaded.sales.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  /**
   *
   * @param orderForm
   */
  private void evaluateOrderEntries(OrderForm orderForm) {
    List<OrderEntry> entries = orderForm.getOrderEntries();
    if (entries == null || entries.isEmpty()) {
      throw new NotFound("orderEntries");
    }
    for (int i = 0; i < entries.size(); i++) {
      OrderEntry entry = entries.get(i);
      entry.setEntryRow(i + 1);
      if (orderForm.getOrderId() == null) {
        entry.setEntryId(null);
      }
      entry.setEntryProduct(productRepository.getReferenceById(entry.getEntryProduct().getProductId()));
    }
  }

  /**
   *
   * @param typeEval
   * @param availabilityUpdate
   */
  private void updateAvailability(OrderEval typeEval, Map<Integer, BigDecimal> availabilityUpdate) {
    List<Integer> ids = availabilityUpdate.keySet().stream().toList();
    if (!ids.isEmpty()) {
      List<Product> productForUpdate = productRepository.findAllProductForUpdate(ids);

      if (typeEval != OrderEval.none) {
        for (Product product: productForUpdate) {
          BigDecimal quantity = availabilityUpdate.get(product.getProductId());
          if (typeEval == OrderEval.push) {
            quantity = product.getProductAvailable().add(quantity);
          }
          else if (typeEval == OrderEval.pull) {
            quantity = product.getProductAvailable().subtract(quantity);
          }
//          log.info("updateAvailability: {} {} {} {}", typeEval, product.getProductId(), availabilityUpdate.get(product.getProductId()), quantity);

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

  /**
   *
   * @param orderForm
   * @return
   */
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

    boolean archived = changes.getOrderState() == OrderState.archived;
    boolean scheduled = changes.getOrderState() == OrderState.scheduled;
    if (archived) {
      orderForm.setOrderState(changes.getOrderState());
    }
    else if (orderForm.getOrderState() == OrderState.canceled) {
      throw new RuntimeException("order is already canceled");
    }

    if (!orderForm.getOrderType().getTypeId().equals(changes.getOrderType().getTypeId())) {
      if (!archived && !scheduled) {
        updateOrderNum(orderForm, +1);
      }
    }

    OrderEval oldEval = archived ? OrderEval.none : orderForm.getOrderType().getTypeEval();
    BeanUtils.copyProperties(changes, orderForm, "orderEntries");

    updateOrderCustomer(orderForm);
    evaluateOrderEntries(orderForm);

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
      removed.forEach(entry -> entry.setEntryOrder(null));

      // Update existing entries
      OrderEval newEval = archived ? OrderEval.none : changes.getOrderType().getTypeEval();
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
        if (change != null) {
          BeanUtils.copyProperties(change, entry, "entryId", "entryOrder");
        }
        if (!archived) {
          entry.setEntryAvailable(entry.getEntryProduct().getProductAvailable());
        }
      }
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
    orderFormRepository.save(orderForm);
  }

  /**
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public OrderForm getOrderById(Integer id) {
    return orderFormRepository.findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));
  }

  /**
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public OrderForm getOrderCopyById(Integer id, boolean content) {
    OrderForm res = new OrderForm();
    OrderForm found = orderFormRepository.findById(id)
      .orElseThrow(() -> new NotFound("Order form not found"));

    BeanUtils.copyProperties(
      found,
      res,
      OrderForm.Fields.orderId,
      OrderForm.Fields.orderEntries
    );

    List<OrderEntry> newEntries = new ArrayList<>();
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
        newEntry.setEntryRow(i + 1);
        newEntry.setEntryAvailable(newEntry.getEntryProduct().getProductAvailable());
        newEntries.add(newEntry);
//        Product product = productRepository.findById(entry.getEntryProduct().getProductId()).orElseThrow();
//        entry.setEntryProduct(product);
      }
    }
    res.setOrderEntries(newEntries);

    res.setOrderState(OrderState.draft);
    return res;
  }

  /**
   *
   * @param orderForm
   */
  private void updateOrderNum(OrderForm orderForm, int step) {
    Integer counter = orderForm.getOrderType().getTypeCounter();
    OrderType orderType = orderTypeRepository.getOrderTypeForUpdate(counter);

    Long num = orderType.getTypeNum();
    OffsetDateTime date = orderForm.getOrderDate();
    OrderNumDto orderNum = null;
    if (step == +1) {
      do {
        orderNum = orderFormRepository.getFirstByOrderNumAndOrderCounterAndOrderStateGreaterThanOrderByOrderNumDesc(
          num, counter, OrderState.archived
        ).orElse(null);
        if (orderNum != null) {
          num = num + 1;
          if (orderForm.getOrderDate().isBefore(orderNum.getOrderDate())) {
            date = orderNum.getOrderDate().plusSeconds(1);
          }
        }
      } while (orderNum != null);

      orderForm.setOrderNum(num);
      orderForm.setOrderDate(date);
      orderType.setTypeNum(num + 1);
      orderTypeRepository.save(orderType);
      log.info("COUNTER+: " + num);
    }
    else if (step == -1) {
      if (num > orderForm.getOrderNum()) {
        num = orderForm.getOrderNum();
        orderType.setTypeNum(num);
        orderTypeRepository.save(orderType);
        log.info("COUNTER-: " + num);
      }
    }
  }

  /**
   *
   * @param orderTypeId
   * @return
   */
  @Transactional(readOnly = true)
  public Optional<OrderForm> getLastOrderByOrderType(Integer orderTypeId) {
    Optional<OrderForm> last = orderFormRepository.findTopByOrderType_TypeIdOrderByOrderNumDesc(orderTypeId);
    OrderType orderType;
    OrderForm orderForm;
    if (last.isPresent()) {
      orderForm = last.get();
      orderType = orderForm.getOrderType();
    }
    else {
      orderForm = new OrderForm();
      orderType = orderTypeRepository.findById(orderTypeId).get();
      orderForm.setOrderType(orderType);
      orderForm.setOrderDate(OffsetDateTime.now());
    }

    orderForm.setOrderState(OrderState.draft);
    if (!orderType.getTypeId().equals(orderType.getTypeCounter())) {
      orderType = orderTypeRepository.findById(orderType.getTypeCounter()).get();
    }
    orderForm.setOrderNum(orderType.getTypeNum());

    return Optional.of(orderForm);
  }

}
