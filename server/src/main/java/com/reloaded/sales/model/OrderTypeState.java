package com.reloaded.sales.model;

import com.reloaded.sales.util.PersistableEnum;

public enum OrderTypeState implements PersistableEnum<Integer> {
  deleted(0),
  active(1);

  @Override
  public Integer getValue() { return value; }
  private final Integer value;
  OrderTypeState(Integer value) { this.value = value; }
}
