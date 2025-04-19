package org.project.tpo07.services;

import org.project.tpo07.dto.FormatResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormatCleanerService {
    private final FormatService formatService;

    public FormatCleanerService(FormatService formatService) {
        this.formatService = formatService;
    }

    public void start() {
        new Thread(() -> {
            try {
                while (true) {
                    List<FormatResult> formatResults = formatService.getAllResults();

                    List<FormatResult> expiredResults = formatResults.stream()
                            .filter(result -> result.getExpiryDate().isBefore(LocalDateTime.now()))
                            .toList();

                    formatService.deleteResults(expiredResults);

                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {}
        }, "FormatCleaner").start();
    }
}
