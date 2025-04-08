package org.project.controllers;

import org.project.exceptions.InvalidBaseException;
import org.project.exceptions.InvalidDigitException;
import org.project.services.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class ConversionController {

    private final ConversionService conversionService;

    private static final int BIN = 2;
    private static final int OCT = 8;
    private static final int DEC = 10;
    private static final int HEX = 16;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/conversion/conversion-result")
    @ResponseBody
    public String convertValue(
            @RequestParam String value,
            @RequestParam int fromBase,
            @RequestParam int toBase
    ) {
        String template;
        try {
            ClassPathResource resource = new ClassPathResource("static/conversion/conversion-result.html");
            template =  StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            template = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";
        }

        String replacedHtml;

        String replaceCommon = template
                .replace("{{ORIGINAL}}", value)
                .replace("{{FROM_BASE}}", String.valueOf(fromBase))
                .replace("{{TO_BASE}}", String.valueOf(toBase));
        try {
            String converted = conversionService.convertBase(value, fromBase, toBase);

            replacedHtml = replaceCommon
                    .replace("{{CONVERTED}}", converted)
                    .replace("{{BIN}}", conversionService.convertBase(value, fromBase, BIN))
                    .replace("{{OCT}}", conversionService.convertBase(value, fromBase, OCT))
                    .replace("{{DEC}}", conversionService.convertBase(value, fromBase, DEC))
                    .replace("{{HEX}}", conversionService.convertBase(value, fromBase, HEX))
                    .replace("{{ERROR_BLOCK}}", "");
        }
        catch (InvalidBaseException | InvalidDigitException e) {
            String errorBlock = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";

            replacedHtml = replaceCommon
                    .replace("{{CONVERTED}}", "-")
                    .replace("{{BIN}}", "-")
                    .replace("{{OCT}}", "-")
                    .replace("{{DEC}}", "-")
                    .replace("{{HEX}}", "-")
                    .replace("{{ERROR_BLOCK}}", errorBlock);
        }

        return replacedHtml;
    }
}