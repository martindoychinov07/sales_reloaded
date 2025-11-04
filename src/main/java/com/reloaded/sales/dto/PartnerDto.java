package com.reloaded.sales.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.reloaded.sales.model.Partner}
 */
@Data
public final class PartnerDto implements Serializable {
  private Integer partnerId;
  private Integer partnerRefId;
  private Integer partnerState;
  private Instant version;
  private String code;
  private String name;
  private String location;
  private String address;
  private String idTags;
  private String cpTags;
  private String noteTags;
}