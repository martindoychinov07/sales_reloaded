package com.reloaded.sales.repository;

import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.model.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm, Integer> {

  Optional<OrderForm> findTopByOrderTypeOrderByOrderNumDesc(OrderType orderType);

}
