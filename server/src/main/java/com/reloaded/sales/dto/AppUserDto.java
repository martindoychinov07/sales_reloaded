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
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.reloaded.sales.model.AppUser}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto implements Serializable {
  private Integer userId;
  @Size(max = 100)
  private String username;
  @Size(max = 100)
  private String userRole;
  private Instant userExpireDate;
  private Instant userLockDate;
  @Size(max = 100)
  private String fullname;
  private String newPassword;
}