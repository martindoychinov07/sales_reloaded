/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.util;

import com.reloaded.sales.dto.filter.PageFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceUtils {

  /**
   * Prepares a PageRequest based on filter and default sort.
   *
   * @param filter      the filter object containing optional page, size, sort, direction
   * @param defaultSort default sort orders to apply if not specified in filter
   * @return a PageRequest with combined sorting
   */
  public static PageRequest paging(PageFilter filter, List<Sort.Order> defaultSort) {
    int page = orElse(filter.getPage(), 0);
    int size = orElse(filter.getSize(), 100);

    List<Sort.Order> orders = new ArrayList<>();

    // Add filter sort if present
    if (filter.getSort() != null && !filter.getSort().isBlank()) {
      Sort.Direction direction = orElse(filter.getDirection(), Sort.Direction.ASC);
      orders.add(new Sort.Order(direction, filter.getSort()));
    }

    // Add default sort orders that are not duplicates of filter.getSort()
    if (defaultSort != null) {
      defaultSort.stream()
        .filter(o -> filter.getSort() == null || !o.getProperty().equalsIgnoreCase(filter.getSort()))
        .forEach(orders::add);
    }

    // Fallback: if orders are still empty, use defaultSort
    if (orders.isEmpty() && defaultSort != null) {
      orders.addAll(defaultSort);
    }

    return PageRequest.of(page, size, Sort.by(orders));
  }

  public static <T> T orElse(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }

  public static String orBlank(String value) {
    return value != null && !value.isBlank() ? value : null;
  }

  public static boolean eq(BigDecimal a, BigDecimal b) {
    return a != null && b != null && a.compareTo(b) == 0;
  }

  public static boolean ge(BigDecimal a, BigDecimal b) {
    return a != null && b != null && a.compareTo(b) >= 0;
  }

  public static boolean gt(BigDecimal a, BigDecimal b) {
    return a != null && b != null && a.compareTo(b) > 0;
  }

  public static <E, T> Specification<E> between(String field, BigDecimal min, BigDecimal max) {
    return (root, query, cb) -> {
      if (min != null && max != null) {
        return cb.between(root.get(field), min, max);
      }
      if (min != null) {
        return cb.ge(root.get(field), min);
      }
      if (max != null) {
        return cb.le(root.get(field), max);
      }
      return cb.conjunction();
    };
  }

  public static <E, T> Specification<E> anyLike(String term, String... fields) {
    return (root, query, cb) -> {

      if (term == null || term.isBlank() || fields == null || fields.length == 0) {
        return cb.conjunction();
      }

      String pattern = "%" + term.toLowerCase() + "%";

      Predicate[] predicates = Arrays.stream(fields)
        .map(field ->
          cb.like(
            cb.lower(root.get(field)),
            pattern
          )
        )
        .toArray(Predicate[]::new);

      return cb.or(predicates);
    };
  }

  public static <E, T extends Comparable<? super T>> Specification<E> ge(String field, T value) {
    return (root, query, cb) ->
      value == null ? cb.conjunction()
        : cb.greaterThanOrEqualTo(root.get(field), value);
  }

  public static <E, T extends Comparable<? super T>> Specification<E> gt(String field, T value) {
    return (root, query, cb) ->
      value == null ? cb.conjunction()
        : cb.greaterThan(root.get(field), value);
  }

  public static <E, T extends Comparable<? super T>> Specification<E> le(String field, T value) {
    return (root, query, cb) ->
      value == null ? cb.conjunction()
        : cb.lessThanOrEqualTo(root.get(field), value);
  }

  public static <E, T extends Comparable<? super T>> Specification<E> lt(String field, T value) {
    return (root, query, cb) ->
      value == null ? cb.conjunction()
        : cb.lessThan(root.get(field), value);
  }

  public static <E, T> Specification<E> eq(String field, T value) {
    return (root, query, cb) ->
      value == null ? cb.conjunction()
        : cb.equal(root.get(field), value);
  }


}

