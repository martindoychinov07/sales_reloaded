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

  Optional<OrderForm> findTopByOrderType_TypeIdOrderByOrderNumDesc(Integer typeId);

  Optional<OrderNumDto> getFirstByOrderNumAndOrderCounterAndOrderStateGreaterThanOrderByOrderNumDesc(
    Long num,
    Integer counter,
    OrderState state
  );

}
