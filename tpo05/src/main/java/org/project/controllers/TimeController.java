package org.project.controllers;

import org.project.config.FallbackProperties;
import org.project.exceptions.InvalidFormatException;
import org.project.exceptions.InvalidTimezoneException;
import org.project.services.TimeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeController {

    private final TimeService timeService;
    private final FallbackProperties fallbackProperties;


    public TimeController(TimeService timeService, FallbackProperties fallbackProperties) {
        this.timeService = timeService;
        this.fallbackProperties = fallbackProperties;
    }

    @GetMapping("/current-time")
    @ResponseBody
    public String getCurrentTime(
            @RequestParam(value = "timezone", required = false) String timezone,
            @RequestParam(value = "format", required = false) String format) {
        //todo make returning in beautiful way

        timezone = timezone == null ? fallbackProperties.getDefaultTimezone() : timezone;
        format = format == null ? fallbackProperties.getDefaultFormat() : format;

        try {
            return timeService.getCurrentFormattedTime(timezone, format);
        } catch (InvalidFormatException | InvalidTimezoneException e) {
            try {
                String fallbackResult = timeService.getCurrentFormattedTime(fallbackProperties.getDefaultTimezone(), fallbackProperties.getDefaultFormat());
                return "Error: " + e.getMessage() + "\nFalling back to default: " + fallbackResult;
            } catch (Exception fallbackError) {
                return "Critical Error during fallback execution: " + fallbackError.getMessage();
            }
        }
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String getCurrentYear() {
        return timeService.getCurrentYear();
    }
}
