/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.function.Function;

public class JsonFlattener {

  /**
   * Flatten JSON matching full-path filter, optionally rename keys.
   *
   * @param root          Root JsonNode
   * @param keysFilter    Full-path keys to flatten (e.g. "user.name.first")
   * @param renameFunc    Optional rename function
   * @param keepUnmatched If true → keep unmatched branches, if false → drop them
   */
  public static Map<String, Object> flatten(
    JsonNode root,
    Collection<String> keysFilter,
    Function<String, String> renameFunc,
    boolean keepUnmatched
  ) {
    Map<String, Object> result = new LinkedHashMap<>();
    walkNode("", root, result, keysFilter, renameFunc, keepUnmatched);
    return result;
  }

  private static void walkNode(
    String path,
    JsonNode node,
    Map<String, Object> result,
    Collection<String> keysFilter,
    Function<String, String> renameFunc,
    boolean keepUnmatched
  ) {

    boolean shouldFlatten = keysFilter == null || keysFilter.contains(path);

    // OBJECT
    if (node.isObject()) {
      Iterator<Map.Entry<String, JsonNode>> fields = node.properties().iterator();
      while (fields.hasNext()) {
        Map.Entry<String, JsonNode> entry = fields.next();
        String childPath = path.isEmpty()
          ? entry.getKey()
          : path + "." + entry.getKey();

        walkNode(childPath, entry.getValue(), result, keysFilter, renameFunc, keepUnmatched);
      }
      return;
    }

    // ARRAY
    if (node.isArray()) {
      for (int i = 0; i < node.size(); i++) {
        String childPath = path + "[" + i + "]";
        walkNode(childPath, node.get(i), result, keysFilter, renameFunc, keepUnmatched);
      }
      return;
    }

    // VALUE (leaf)
    if (shouldFlatten) {
      String finalKey = renameFunc != null ? renameFunc.apply(path) : path;
      result.put(finalKey, convertLeaf(node));
    }
    else if (keepUnmatched) {
      String finalKey = renameFunc != null ? renameFunc.apply(path) : path;
      result.put(finalKey, convertLeaf(node));
    }
  }

  /**
   * Preserve correct Java type
   */
  private static Object convertLeaf(JsonNode node) {
    if (node.isInt()) return node.asInt();
    if (node.isLong()) return node.asLong();
    if (node.isDouble()) return node.asDouble();
    if (node.isBoolean()) return node.asBoolean();
    if (node.isTextual()) return node.asText();
    if (node.isNull()) return null;
    return node.toString(); // fallback for other types
  }
}