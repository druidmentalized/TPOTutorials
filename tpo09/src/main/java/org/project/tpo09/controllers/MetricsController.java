package org.project.tpo09.controllers;

import org.project.tpo09.dto.BmiDto;
import org.project.tpo09.dto.BmrDto;
import org.project.tpo09.exceptions.InvalidDataException;
import org.project.tpo09.exceptions.InvalidGenderException;
import org.project.tpo09.services.MetricsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
public class MetricsController {

    private static final String ERROR_HEADER = "Error";

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping(value = "/BMI", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BmiDto> calculateBMIJson(@RequestParam double weight,
                                                   @RequestParam double height) {
        try {
            return ResponseEntity.ok(metricsService.calculateBmi(weight, height));
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().header(ERROR_HEADER, e.getMessage()).build();
        }
    }

    @GetMapping(value = "/BMI", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> calculateBMIText(@RequestParam double weight,
                                                   @RequestParam double height) {
        try {
            return ResponseEntity.ok(String.valueOf(metricsService.calculateBmi(weight, height).getBmi()));
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().header(ERROR_HEADER, e.getMessage()).build();
        }
    }

    @GetMapping("/BMR/{gender}")
    public ResponseEntity<BmrDto> calculateBMR(@PathVariable String gender,
                                               @RequestParam double weight,
                                               @RequestParam double height,
                                               @RequestParam int age) {
        try {
            return ResponseEntity.ok(metricsService.calculateBmr(gender, weight, height, age));
        } catch (InvalidDataException e) {
            return ResponseEntity.status(499).header(ERROR_HEADER, e.getMessage()).build();
        } catch (InvalidGenderException e) {
            return ResponseEntity.badRequest().header(ERROR_HEADER, e.getMessage()).build();
        }
    }
}
