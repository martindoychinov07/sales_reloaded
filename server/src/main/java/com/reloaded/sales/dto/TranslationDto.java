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
  @NotNull
  @Size(max = 200)
  private String toEn;
  @NotNull
  @Size(max = 200)
  private String toBg;
  @NotNull
  @Size(max = 200)
  private String toT1;
  @NotNull
  @Size(max = 200)
  private String toT2;
  @NotNull
  @Size(max = 200)
  private String toT3;
  @NotNull
  @Size(max = 200)
  private String toT4;
  @NotNull
  @Size(max = 200)
  private String toT5;
}