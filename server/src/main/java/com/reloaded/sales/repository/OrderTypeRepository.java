package com.reloaded.sales.repository;

import com.reloaded.sales.model.OrderType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Integer> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT s FROM OrderType s WHERE s.typeId = :id")
  @QueryHints(@QueryHint(name="jakarta.persistence.lock.timeout", value="5000"))
  OrderType getOrderTypeForUpdate(Integer id);

}
