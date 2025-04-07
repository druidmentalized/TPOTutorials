package org.project.controllers;

import org.project.config.FallbackProperties;
import org.project.exceptions.InvalidFormatException;
import org.project.exceptions.InvalidTimezoneException;
import org.project.services.TimeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TimeController {

    private final TimeService timeService;
    private final FallbackProperties fallbackProperties;


    public TimeController(TimeService timeService, FallbackProperties fallbackProperties) {
        this.timeService = timeService;
        this.fallbackProperties = fallbackProperties;
    }

    @GetMapping("/current-time")
    public String redirectToStaticCurrentTime(
            @RequestParam(value = "timezone", required = false) String timezone,
            @RequestParam(value = "format", required = false) String format) {

        List<String> params = new ArrayList<>();

        if (timezone != null) {
            params.add("timezone=" + timezone);
        }

        if (format != null) {
            params.add("format=" + format);
        }

        String paramString = String.join("&", params);

        return "redirect:time/current-time.html" + (paramString.isEmpty() ? "" : "?" + paramString);
    }

    @GetMapping("/api/current-time")
    @ResponseBody
    public Map<String, String> getCurrentTime(
            @RequestParam(value = "timezone", required = false) String timezone,
            @RequestParam(value = "format", required = false) String format) {

        timezone = (timezone == null || timezone.isBlank())
                ? fallbackProperties.getDefaultTimezone()
                : timezone;

        format = (format == null || format.isBlank())
                ? fallbackProperties.getDefaultFormat()
                : format;

        Map<String, String> response = new HashMap<>();
        try {
            String result = timeService.getCurrentFormattedTime(timezone, format);
            response.put("time", result);
        } catch (InvalidFormatException | InvalidTimezoneException e) {
            response.put("error", e.getMessage());
            try {
                String fallback = timeService.getCurrentFormattedTime(
                        fallbackProperties.getDefaultTimezone(),
                        fallbackProperties.getDefaultFormat()
                );
                response.put("time", fallback);
                response.put("note", "Fallback values used");
            } catch (Exception fatal) {
                response.put("time", "Critical error: " + fatal.getMessage());
            }
        }

        return response;
    }

    @GetMapping("/current-year")
    public String redirectToStaticCurrentYear() {
        return "redirect:time/current-year.html";
    }

    @GetMapping("/api/current-year")
    @ResponseBody
    public String getCurrentYear() {
        return timeService.getCurrentYear();
    }
}
