/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.reloaded.sales.model.Contact}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto implements Serializable {
  private Integer contactId;
  private Integer contactVersion;
  private Integer contactRefId;
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
  private String contactCode1;
  @Size(max = 100)
  private String contactCode2;
  @Size(max = 100)
  private String contactCode3;
  @Size(max = 100)
  private String contactOwner;
  @Size(max = 100)
  private String contactResp;
}