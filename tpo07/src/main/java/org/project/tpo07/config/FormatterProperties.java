package org.project.tpo07.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "formatter")
public class FormatterProperties {
    private String storagePath;
    private int standardRetention;

    public String getStoragePath() {
        return storagePath;
    }
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public int getStandardRetention() {
        return standardRetention;
    }
    public void setStandardRetention(int standardRetention) {
        this.standardRetention = standardRetention;
    }
}
