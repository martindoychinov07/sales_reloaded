/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.dto.filter;

import com.reloaded.sales.util.PageFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingFilter implements PageFilter {

  private String settingKey;
  private String settingGroup;
  private String settingNote;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
