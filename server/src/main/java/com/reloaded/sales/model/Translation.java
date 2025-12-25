package com.reloaded.sales.model;

import jakarta.persistence.*;
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
  @Column(name = "t_key", nullable = false, length = 200)
  private String translationKey;

  @Size(max = 200)
  @Column(name = "t_en", length = 200)
  private String en;

  @Size(max = 200)
  @Column(name = "t_bg", length = 200)
  private String bg;

  @Size(max = 200)
  @Column(name = "t_t1", length = 200)
  private String t1;

  @Size(max = 200)
  @Column(name = "t_t2", length = 200)
  private String t2;

  @Size(max = 200)
  @Column(name = "t_t3", length = 200)
  private String t3;

  @Size(max = 200)
  @Column(name = "t_t4", length = 200)
  private String t4;

  @Size(max = 200)
  @Column(name = "t_t5", length = 200)
  private String t5;

}