package com.reloaded.sales.model;

import com.reloaded.sales.converter.PersistableEnum;

public enum ProductState  implements PersistableEnum<Integer> {
  deleted(0),
  active(1);

  @Override
  public Integer getValue() { return value; }
  private final Integer value;
  ProductState(Integer value) { this.value = value; }
}
