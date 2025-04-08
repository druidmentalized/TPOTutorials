package org.project.controllers;

import org.project.exceptions.InvalidBaseException;
import org.project.exceptions.InvalidDigitException;
import org.project.services.ConversionService;
import org.project.utils.HtmlLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        String template = HtmlLoader.loadHtml("static/conversion/conversion-result.html");

        String converted;
        String bin;
        String oct;
        String dec;
        String hex;
        String errorMsg = "";

        try {
            converted = conversionService.convertBase(value, fromBase, toBase);

            bin = conversionService.convertBase(value, fromBase, BIN);
            oct = conversionService.convertBase(value, fromBase, OCT);
            dec = conversionService.convertBase(value, fromBase, DEC);
            hex = conversionService.convertBase(value, fromBase, HEX);
        }
        catch (InvalidBaseException | InvalidDigitException e) {
            converted = "-";

            bin = "-";
            oct = "-";
            dec = "-";
            hex = "-";

            errorMsg = "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";
        }

        return template
                .replace("{{ORIGINAL}}", value)
                .replace("{{FROM_BASE}}", String.valueOf(fromBase))
                .replace("{{TO_BASE}}", String.valueOf(toBase))
                .replace("{{CONVERTED}}", converted)
                .replace("{{BIN}}", bin)
                .replace("{{OCT}}", oct)
                .replace("{{DEC}}", dec)
                .replace("{{HEX}}", hex)
                .replace("{{ERROR_BLOCK}}", errorMsg);
    }
}