package com.reloaded.sales.util;

import java.util.function.Function;

public record CsvColumn<T>(
  String key,
  String label,
  Function<T, ?> extractor
) {}
