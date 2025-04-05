package org.project.services;

import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class TimeService {
    public String getCurrentFormattedTime(String timezone, String format) {
        //todo make logic of obtaining time
    }

    public String getCurrentYear() {
        return String.valueOf(Year.now());
    }
}
