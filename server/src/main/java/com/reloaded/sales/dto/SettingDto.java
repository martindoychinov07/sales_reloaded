package com.reloaded.sales.dto;

import com.reloaded.sales.model.SettingType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.reloaded.sales.model.Setting}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingDto implements Serializable {
  private Integer settingId;
  private SettingType settingType;
  @Size(max = 100)
  private String settingKey;
  @Size(max = 300)
  private String settingNote;
  @Size(max = 300)
  private String settingValue;
  @Size(max = 100)
  private String settingGroup;
}