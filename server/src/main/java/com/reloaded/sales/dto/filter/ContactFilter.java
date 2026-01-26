package com.reloaded.sales.dto.filter;

import com.reloaded.sales.model.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactFilter implements PageFilter {

  private String contactCode;
  private String contactLocation;
  private String contactText;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
