/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

  private String ccy;
}
