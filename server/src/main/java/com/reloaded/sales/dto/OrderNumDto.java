/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto;

import java.time.OffsetDateTime;

public interface OrderNumDto {
  Integer getOrderCounter();
  Long getOrderNum();
  OffsetDateTime getOrderDate();
}
