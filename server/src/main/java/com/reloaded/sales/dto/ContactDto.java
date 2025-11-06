package com.reloaded.sales.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.reloaded.sales.model.Contact}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
  private Integer contactId;
  private Integer contactVersion;
  @Size(max = 30)
  private String contactCode;
  @Size(max = 200)
  private String contactName;
  @Size(max = 200)
  private String contactLocation;
  @Size(max = 300)
  private String contactAddress;
  @Size(max = 300)
  private String contactNote;
  @Size(max = 100)
  private String contactTagUic;
  @Size(max = 100)
  private String contactTagVat;
  @Size(max = 100)
  private String contactTagTai;
  @Size(max = 100)
  private String contactTagOwn;
  @Size(max = 100)
  private String contactTagResp;
}