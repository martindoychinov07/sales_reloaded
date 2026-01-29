/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParseText {

  public static Map<String, String> parseStringToMap(String text) {
    if (text == null || text.trim().isEmpty()) {
      return Map.of(); // empty map
    }

    return Arrays.stream(text.split(","))  // split by comma
      .map(s -> s.split(":", 2)) // split each pair by colon
      .filter(arr -> arr.length == 2)      // ignore invalid pairs
      .collect(Collectors.toMap(
        arr -> arr[0].trim(),              // key
        arr -> arr[1].trim()               // value
      ));
  }

  public static Map.Entry<String, String> parseKeyValue(String text) {
    // Remove outer spaces
    text = text.trim();

    // Regex: "key":value
    String regex = "\"([^\"]+)\":(.*)";
    Matcher m = Pattern.compile(regex).matcher(text);

    if (!m.matches()) {
      throw new IllegalArgumentException("Invalid key/value format: " + text);
    }

    String key = m.group(1).trim();
    String value = m.group(2).trim();

    // Remove quotes if quoted
    if (value.startsWith("\"") && value.endsWith("\"")) {
      value = value.substring(1, value.length() - 1);
    }

    return Map.entry(key, value);
  }

  public static Map<String, String> parseKeyValueLine(String line) {
    Map<String, String> result = new LinkedHashMap<>();

    // Split by commas not inside quotes
    String[] pairs = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

    for (String pair : pairs) {
      var entry = parseKeyValue(pair);
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

}
