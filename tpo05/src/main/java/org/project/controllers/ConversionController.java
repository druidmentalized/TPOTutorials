package org.project.controllers;

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
    ) throws IOException {
        String template = loadHtmlTemplate("static/conversion/conversion-result.html");
        String errorBlock;
        String replacedHtml;

        System.out.println("INSIDE POST METHOD");

        try {
            String converted = conversionService.convertBase(value, fromBase, toBase);

            replacedHtml = template
                    .replace("{{ORIGINAL}}", value)
                    .replace("{{FROM_BASE}}", String.valueOf(fromBase))
                    .replace("{{TO_BASE}}", String.valueOf(toBase))
                    .replace("{{CONVERTED}}", converted)
                    .replace("{{BIN}}", conversionService.convertBase(value, fromBase, BIN))
                    .replace("{{OCT}}", conversionService.convertBase(value, fromBase, OCT))
                    .replace("{{DEC}}", conversionService.convertBase(value, fromBase, DEC))
                    .replace("{{HEX}}", conversionService.convertBase(value, fromBase, HEX))
                    .replace("{{ERROR_BLOCK}}", ""); // no error

        } catch (IllegalArgumentException e) {
            errorBlock = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";

            replacedHtml = template
                    .replace("{{ORIGINAL}}", value)
                    .replace("{{FROM_BASE}}", String.valueOf(fromBase))
                    .replace("{{TO_BASE}}", String.valueOf(toBase))
                    .replace("{{CONVERTED}}", "-")
                    .replace("{{BIN}}", "-")
                    .replace("{{OCT}}", "-")
                    .replace("{{DEC}}", "-")
                    .replace("{{HEX}}", "-")
                    .replace("{{ERROR_BLOCK}}", errorBlock);
        }

        return replacedHtml;
    }

    private String loadHtmlTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}