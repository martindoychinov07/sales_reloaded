/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.reloaded.sales.model.Translation}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationDto implements Serializable {
  private Integer translationId;
  @NotNull
  @Size(max = 200)
  private String translationKey;
  @Size(max = 200)
  private String en;
  @Size(max = 200)
  private String bg;
  @Size(max = 200)
  private String t1;
  @Size(max = 200)
  private String t2;
  @Size(max = 200)
  private String t3;
  @Size(max = 200)
  private String t4;
  @Size(max = 200)
  private String t5;
}