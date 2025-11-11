package com.example.spacecatmarket.javawebkpi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "feature")
public class FeatureToggleProperties {
    Map<String, Boolean> toggles;

}