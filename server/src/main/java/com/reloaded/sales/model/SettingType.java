/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.model;

import com.reloaded.sales.util.PersistableEnum;

public enum SettingType implements PersistableEnum<Integer> {
  type_text(0),
  type_number(1);

  @Override
  public Integer getValue() { return value; }
  private final Integer value;
  SettingType(Integer value) { this.value = value; }
}
