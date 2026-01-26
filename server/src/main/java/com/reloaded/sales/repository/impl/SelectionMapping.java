package com.reloaded.sales.repository.impl;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Selection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class SelectionMapping {
  private String dtoField;
  private Path<?> path;
  private Selection<?> selection;
}
