package org.project.tpo08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FormatterConfig {
    private final FormatterProperties properties;

    public FormatterConfig(FormatterProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Path resultStoragePath() {
        return Paths.get(properties.getStoragePath());
    }

    @Bean int standardRetention() {
        return properties.getStandardRetention();
    }
}
