/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class ManualAuditedEntity {

//  @Column(name = "created_at", updatable = false)
//  protected Instant createdAt;

  @Column(name = "updated_at")
  protected Instant updatedAt;

//  @Column(name = "created_by")
//  protected Integer createdBy;

  @Column(name = "updated_by")
  protected Integer updatedBy;
}

