package org.project.services;

import org.project.exceptions.InvalidFormatException;
import org.project.exceptions.InvalidTimezoneException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {
    public String getCurrentFormattedTime(String timezone, String format) {
        try {
            ZoneId zone = ZoneId.of(timezone);
            ZonedDateTime now = ZonedDateTime.now(zone);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return now.format(formatter);
        } catch (DateTimeException e) {
            throw new InvalidTimezoneException("Invalid timezone: " + timezone);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Invalid format pattern: " + format);
        }
    }

    public String getCurrentYear() {
        return String.valueOf(Year.now());
    }
}
