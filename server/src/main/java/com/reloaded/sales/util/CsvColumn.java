/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.util;

import java.util.function.Function;

public record CsvColumn<T>(
  String key,
  String label,
  Function<T, ?> extractor
) {}
