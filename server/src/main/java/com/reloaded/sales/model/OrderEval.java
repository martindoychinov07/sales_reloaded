package com.reloaded.sales.model;

import com.reloaded.sales.util.PersistableEnum;

public enum OrderEval implements PersistableEnum<Integer> {
  none(0),
  init(1),
  push(2),
  pull(3);

  @Override
  public Integer getValue() { return value; }
  private final Integer value;
  OrderEval(Integer value) { this.value = value; }
}
