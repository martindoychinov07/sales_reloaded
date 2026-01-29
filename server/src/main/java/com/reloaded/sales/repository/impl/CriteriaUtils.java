/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.repository.impl;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CriteriaUtils {

  private CriteriaUtils() {}

  /** Root field */
  public static Selection<?> select(Root<?> root, String fieldName, String alias) {
    return root.get(fieldName).alias(alias);
  }

  /** Join field */
  public static Selection<?> select(Join<?, ?> join, String fieldName, String alias) {
    return join.get(fieldName).alias(alias);
  }

  /** Path already */
  public static Selection<?> select(Path<?> path, String alias) {
    return path.alias(alias);
  }

  public static <T> void applySorting(
    CriteriaBuilder cb,
    CriteriaQuery<?> cq,
    Root<T> root,
    Map<String, Join<?, ?>> joins,
    Pageable pageable) {

    if (!pageable.getSort().isSorted()) return;

    List<Order> orders = new ArrayList<>();

    for (Sort.Order sort : pageable.getSort()) {
      Path<?> path = resolvePath(root, joins, sort.getProperty());
      orders.add(sort.isAscending() ? cb.asc(path) : cb.desc(path));
    }

    cq.orderBy(orders);
  }

  public static Path<?> resolvePath(
    Root<?> root,
    Map<String, Join<?, ?>> joins,
    String property) {

    if (property.contains(".")) {
      String[] parts = property.split("\\.");
      Join<?, ?> join = joins.get(parts[0]);
      return join.get(parts[1]);
    }
    return root.get(property);
  }
}
