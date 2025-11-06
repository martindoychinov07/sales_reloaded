package com.reloaded.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "translation")
public class Translation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_id_gen")
  @SequenceGenerator(name = "translation_id_gen", sequenceName = "translation_sequence", allocationSize = 1)
  @Column(name = "t_id", nullable = false)
  private Integer translationId;

  @NotNull
  @Column(name = "t_language_code", nullable = false)
  private String translationLang;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_key", nullable = false, length = 200)
  private String translationKey;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_value", nullable = false, length = 200)
  private String translationValue;

  @Column(name = "t_expired")
  private Instant translationExpired;

}