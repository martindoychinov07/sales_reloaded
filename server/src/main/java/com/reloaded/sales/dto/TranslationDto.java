package com.reloaded.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.reloaded.sales.model.Translation}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationDto implements Serializable {
  private Integer translationId;
  @NotNull
  private String translationLang;
  @NotNull
  @Size(max = 200)
  private String translationKey;
  @NotNull
  @Size(max = 200)
  private String translationValue;
  private Instant translationExpired;
}