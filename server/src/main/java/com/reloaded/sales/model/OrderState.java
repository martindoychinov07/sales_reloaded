package com.reloaded.sales.model;

import com.reloaded.sales.converter.PersistableEnum;

public enum OrderState implements PersistableEnum<Integer> {
  canceled(0),
  archived(1),
  draft(2),
  scheduled(3),
  in_progress(4),
  for_review(5),
  reviewed(6),
  for_approve(7),
  approved(8),
  finished(9);

  @Override
  public Integer getValue() {return value; }
  private final Integer value;
  OrderState(Integer value) { this.value = value; }
}
