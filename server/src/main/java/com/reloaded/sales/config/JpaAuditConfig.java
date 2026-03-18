/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables JPA Auditing support in the application.
 * Used for automatically filling audit fields
 * such as createdAt, updatedAt, createdBy, updatedBy.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditConfig {

}

