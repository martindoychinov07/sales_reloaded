package com.reloaded.sales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables JPA Auditing support in the application.
 * Used for automatically filling audit fields
 * such as createdAt, updatedAt, createdBy, updatedBy.
 */
@Configuration // Marks this class as a Spring configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditConfig {

}

