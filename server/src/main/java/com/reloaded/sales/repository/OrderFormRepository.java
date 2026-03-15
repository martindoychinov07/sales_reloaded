/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.repository;

import com.reloaded.sales.dto.OrderNumDto;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.model.OrderState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm, Integer> {

  @Query("""
    select o
    from OrderForm o
    where o.orderType.typeId = :orderTypeId
      and o.orderState > com.reloaded.sales.model.OrderState.archived
    order by o.orderNum desc
  """)
  List<OrderForm> findLastActiveForType(
    @Param("orderTypeId") Integer orderTypeId,
    Pageable pageable
  );

  @Query("""
    select 
      o.orderCounter as orderCounter,
      o.orderNum as orderNum,
      o.orderDate as orderDate
    from OrderForm o
    where o.orderNum = :orderNum
      and o.orderCounter = :orderCounter
      and o.orderState > com.reloaded.sales.model.OrderState.archived
    """)
  List<OrderNumDto> findOrderNum(
    @Param("orderNum") Long orderNum,
    @Param("orderCounter") Integer orderCounter,
    Pageable pageable
  );

  List<OrderForm> findByOrderIdInOrderByOrderDateAsc(Iterable<Integer> integers);
}
