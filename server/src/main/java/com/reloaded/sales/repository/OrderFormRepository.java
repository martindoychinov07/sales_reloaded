package com.reloaded.sales.repository;

import com.reloaded.sales.model.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFormRepository extends JpaRepository<OrderForm, Integer> {
}
