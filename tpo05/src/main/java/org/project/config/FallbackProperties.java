package org.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fallback")
public class FallbackProperties {
    private String defaultTimezone;
    private String defaultFormat;

    public String getDefaultTimezone() {
        return defaultTimezone;
    }
    public void setDefaultTimezone(String defaultTimezone) {
        this.defaultTimezone = defaultTimezone;
    }

    public String getDefaultFormat() {
        return defaultFormat;
    }
    public void setDefaultFormat(String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
}