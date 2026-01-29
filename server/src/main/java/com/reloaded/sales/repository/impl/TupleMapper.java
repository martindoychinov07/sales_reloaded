/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.repository.impl;

import jakarta.persistence.Tuple;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.TupleElement;

import java.util.*;

import java.util.*;

public final class TupleMapper {

  private TupleMapper() { }

  public static <T> List<T> mapToDto(List<Tuple> tuples, Class<T> dtoCls) {
    if (tuples == null || tuples.isEmpty()) {
      return Collections.emptyList();
    }

    try {
      Map<String, Method> setters = resolveSetters(dtoCls);
      List<T> result = new ArrayList<>(tuples.size());

      for (Tuple tuple : tuples) {
        T dto = dtoCls.getDeclaredConstructor().newInstance();

        for (TupleElement<?> element : tuple.getElements()) {
          String alias = element.getAlias();
          if (alias == null) {
            throw new IllegalStateException(
              "Tuple element without alias: " + element
            );
          }

          Method setter = setters.get(alias);
          if (setter != null) {
            setter.invoke(dto, tuple.get(alias));
          }
        }

        result.add(dto);
      }

      return result;
    }
    catch (Exception e) {
      throw new RuntimeException("Failed to map Tuple to DTO: " + dtoCls.getSimpleName(), e);
    }
  }

  private static Map<String, Method> resolveSetters(Class<?> dtoCls) {
    return Arrays.stream(dtoCls.getMethods())
      .filter(m -> m.getName().startsWith("set") && m.getParameterCount() == 1)
      .collect(Collectors.toMap(
        TupleMapper::setterToPropertyName,
        m -> m
      ));
  }

  /**
   * JavaBeans-compliant setter → property name conversion
   */
  private static String setterToPropertyName(Method m) {
    String name = m.getName().substring(3); // remove "set"

    if (name.length() == 1) {
      return name.toLowerCase();
    }

    // JavaBeans rule: if first two letters are uppercase, keep as-is
    if (Character.isUpperCase(name.charAt(0)) &&
      Character.isUpperCase(name.charAt(1))) {
      return name;
    }

    return Character.toLowerCase(name.charAt(0)) + name.substring(1);
  }
}
