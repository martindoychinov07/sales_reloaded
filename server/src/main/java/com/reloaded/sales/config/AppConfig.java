package com.reloaded.sales.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class that maps properties with prefix "app"
 * from application.properties or application.yml.
 */
@Data // Lombok: generates getters, setters, toString, equals, hashCode
@Component // Registers this class as a Spring bean
@ConfigurationProperties(prefix = "app") // Binds properties like app.ccy
public class AppConfig {

  /**
   * Application default currency.
   * Example property: app.ccy=EUR
   */
  private String ccy;
}