package com.reloaded.sales.util;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextNormalizationListener {

  private static final Map<Class<?>, List<Field>> CACHE = new ConcurrentHashMap<>();

  @PrePersist
  @PreUpdate
  public void normalize(Object entity) {

    List<Field> fields = CACHE.computeIfAbsent(
      entity.getClass(),
      this::findNormalizeFields
    );

    for (Field field : fields) {
      try {
        String value = (String) field.get(entity);
        if (value != null) {
          value = value.trim().replaceAll("\\s+", " ");
          field.set(entity, value);
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private List<Field> findNormalizeFields(Class<?> type) {
    List<Field> result = new ArrayList<>();

    for (Field f : type.getDeclaredFields()) {
      if (f.getType() == String.class &&
        f.isAnnotationPresent(NormalizeText.class)) {

        f.setAccessible(true);
        result.add(f);
      }
    }
    return result;
  }
}