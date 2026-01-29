/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto.filter;

import org.springframework.data.domain.Sort;

public interface PageFilter {

  Integer getPage();
  Integer getSize();
  String getSort();
  Sort.Direction getDirection();

}
