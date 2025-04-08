package org.project.controllers;

import org.project.config.FallbackProperties;
import org.project.exceptions.InvalidFormatException;
import org.project.exceptions.InvalidTimezoneException;
import org.project.services.TimeService;
import org.project.utils.HtmlLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

        String template = HtmlLoader.loadHtml("static/time/current-time.html");

        timezone = (timezone == null || timezone.isBlank())
                ? fallbackProperties.getDefaultTimezone()
                : timezone;

        format = (format == null || format.isBlank())
                ? fallbackProperties.getDefaultFormat()
                : format;

        String time;
        String errorMsg = "";

        try {
            time = timeService.getCurrentFormattedTime(timezone, format);
        } catch (InvalidFormatException | InvalidTimezoneException e) {
            errorMsg = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";
            try {
                time = timeService.getCurrentFormattedTime(
                        fallbackProperties.getDefaultTimezone(),
                        fallbackProperties.getDefaultFormat()
                );
            } catch (Exception ex) {
                time = "Undefined";
                errorMsg = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";
            }
        }

        return template
                .replace("{{TIME}}", time)
                .replace("{{ERROR_BLOCK}}", errorMsg);
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String getCurrentYear() {
        String template = HtmlLoader.loadHtml("static/time/current-year.html");

        String year;
        String errorMsg = "";
        try {
            year = timeService.getCurrentYear();
        } catch (Exception e) {
            year = "Undefined";
            errorMsg = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";
        }

        return template
                .replace("{{YEAR}}", "Year: " + year)
                .replace("{{ERROR_BLOCK}}", errorMsg);
    }
}