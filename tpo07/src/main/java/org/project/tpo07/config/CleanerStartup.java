package org.project.tpo07.config;

import jakarta.annotation.PostConstruct;
import org.project.tpo07.services.FormatCleanerService;
import org.springframework.stereotype.Component;

@Component
public class CleanerStartup {
    private final FormatCleanerService cleanerService;

    public CleanerStartup(FormatCleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    @PostConstruct
    public void init() {
        cleanerService.start();
    }
}
