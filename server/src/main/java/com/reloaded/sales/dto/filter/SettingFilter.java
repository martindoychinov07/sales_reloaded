package com.reloaded.sales.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingFilter implements PageFilter {

  private String settingKey;
  private String settingGroup;
  private String settingNote;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
