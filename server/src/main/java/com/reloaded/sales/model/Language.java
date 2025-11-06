package com.reloaded.sales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "language")
public class Language {
  @Id
  @Size(max = 10)
  @Column(name = "l_code", nullable = false, length = 10)
  private String langCode;

  @NotNull
  @Column(name = "l_order", nullable = false)
  private Integer langOrder;

  @Size(max = 10)
  @Column(name = "l_name", length = 10)
  private String langName;

  @Size(max = 10)
  @Column(name = "l_country", length = 10)
  private String langCountry;

  @Size(max = 10)
  @Column(name = "l_variant", length = 10)
  private String langVariant;

}