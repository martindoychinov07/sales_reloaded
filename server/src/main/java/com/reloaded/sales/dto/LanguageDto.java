package com.reloaded.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.reloaded.sales.model.Language}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDto {
  @Size(max = 10)
  private String langCode;
  @NotNull
  private Integer langOrder;
  @Size(max = 10)
  private String langName;
  @Size(max = 10)
  private String langCountry;
  @Size(max = 10)
  private String langVariant;
}