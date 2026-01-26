package com.reloaded.sales.dto;

import java.time.OffsetDateTime;

public interface OrderNumDto {
  Integer getOrderCounter();
  Long getOrderNum();
  OffsetDateTime getOrderDate();
}
