package com.betalyze.shared.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.betalyze")
@EntityScan(basePackages = "com.betalyze")
public class Config {
}
