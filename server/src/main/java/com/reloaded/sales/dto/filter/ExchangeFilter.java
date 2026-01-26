package com.reloaded.sales.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeFilter implements PageFilter {

  private String exchangeBase;
  private String exchangeTarget;

  private Integer page;
  private Integer size;
  private String sort;
  private Sort.Direction direction;

}
