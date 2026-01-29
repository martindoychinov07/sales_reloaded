/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
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
@Table(name = "setting")
public class Setting {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setting_id_gen")
  @SequenceGenerator(name = "setting_id_gen", sequenceName = "setting_sequence", allocationSize = 1)
  @Column(name = "s_id", nullable = false)
  private Integer settingId;

  @Column(name = "s_type")
  private SettingType settingType;

  @Size(max = 100)
  @Column(name = "s_key", length = 100)
  private String settingKey;

  @Size(max = 300)
  @Column(name = "s_note", length = 300)
  private String settingNote;

  @Size(max = 300)
  @Column(name = "s_value", length = 300)
  private String settingValue;

  @Size(max = 100)
  @Column(name = "s_group", length = 100)
  private String settingGroup;

}