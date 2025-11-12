package com.reloaded.sales.model;

import jakarta.persistence.*;
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
@Table(name = "translation")
public class Translation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_id_gen")
  @SequenceGenerator(name = "translation_id_gen", sequenceName = "translation_sequence", allocationSize = 1)
  @Column(name = "t_id", nullable = false)
  private Integer translationId;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_key", nullable = false, length = 200)
  private String translationKey;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_en", nullable = false, length = 200)
  private String toEn;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_bg", nullable = false, length = 200)
  private String toBg;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_t1", nullable = false, length = 200)
  private String toT1;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_t2", nullable = false, length = 200)
  private String toT2;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_t3", nullable = false, length = 200)
  private String toT3;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_t4", nullable = false, length = 200)
  private String toT4;

  @Size(max = 200)
  @NotNull
  @Column(name = "t_t5", nullable = false, length = 200)
  private String toT5;

}