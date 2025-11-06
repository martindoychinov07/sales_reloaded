package com.reloaded.sales.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryProductDto {
  private Integer productId;
  private Integer productVersion;
  private Integer productRefId;
  @Size(max = 200)
  private String productName;
  private Integer productUnits;
  @Size(max = 30)
  private String productMeasure;
  private Integer productAvailable;
}