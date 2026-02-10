package com.reloaded.sales.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ModelMapper, used for mapping
 * between DTOs and entities in the application.
 */
@Configuration // Marks this as a Spring configuration class
public class MapperConfig {

  /**
   * Creates a ModelMapper bean with custom configuration.
   *
   * - MatchingStrategies.STRICT: ensures only exact matching
   *   fields are mapped (safer for production).
   * - setSkipNullEnabled(true): avoids overwriting target fields
   *   with null values from source.
   */
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setSkipNullEnabled(true);
    return modelMapper;
  }
}