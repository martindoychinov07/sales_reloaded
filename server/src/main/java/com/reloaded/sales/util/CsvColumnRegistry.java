package com.reloaded.sales.util;

import java.util.*;
import java.util.function.Function;

public class CsvColumnRegistry<T> {

  private final Map<String, CsvColumn<T>> columns;

  private CsvColumnRegistry(Map<String, CsvColumn<T>> columns) {
    this.columns = Map.copyOf(columns); // immutable
  }

  public static <T> Builder<T> builder() {
    return new Builder<>();
  }

  public List<CsvColumn<T>> resolve(List<String> keys) {
    if (keys == null || keys.isEmpty()) {
      return List.copyOf(columns.values());
    }
    return keys.stream()
      .map(columns::get)
      .filter(Objects::nonNull)
      .toList();
  }

  public Set<String> availableKeys() {
    return columns.keySet();
  }

  // --- Builder inner class ---
  public static class Builder<T> {
    private final LinkedHashMap<String, CsvColumn<T>> map = new LinkedHashMap<>();

    public Builder<T> column(String key, String label, Function<T, ?> extractor) {
      map.put(key, new CsvColumn<>(key, label, extractor));
      return this;
    }

    public CsvColumnRegistry<T> build() {
      return new CsvColumnRegistry<>(map);
    }
  }
}
