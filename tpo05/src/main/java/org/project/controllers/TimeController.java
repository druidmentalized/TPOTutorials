package org.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TimeController {

    @GetMapping("/current-time")
    public void getCurrentTime(
            @RequestParam(value = "timezone", required = false) String timezone,
            @RequestParam(value = "format", required = false) String format) {

    }

    @GetMapping("/current-year")
    public void getCurrentYear() {

    }
}
