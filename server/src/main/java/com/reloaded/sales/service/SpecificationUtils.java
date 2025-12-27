package com.reloaded.sales.service;

import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.time.LocalDate;

public class SpecificationUtils {

  public static <T> Specification<T> dateBetween(String field, OffsetDateTime start, OffsetDateTime end) {
    return (root, query, cb) -> {
      System.out.println("??? " + start + " " + end);
      if (start != null && end != null) {
        return cb.between(root.get(field), start, end);
      }
      if (start != null) {
        return cb.greaterThanOrEqualTo(root.get(field), start);
      }
      if (end != null) {
        return cb.lessThanOrEqualTo(root.get(field), end);
      }
      return cb.conjunction();
    };
  }

}
