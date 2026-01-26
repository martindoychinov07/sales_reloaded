package com.reloaded.sales.dto.filter;

import org.springframework.data.domain.Sort;

public interface PageFilter {

  Integer getPage();
  Integer getSize();
  String getSort();
  Sort.Direction getDirection();

}
