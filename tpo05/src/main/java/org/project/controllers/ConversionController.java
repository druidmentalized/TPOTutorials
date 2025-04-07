/*
package org.project.controllers;

import org.project.services.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@Controller
public class ConversionController {
    private static final char[] DIGITS = (
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+-=[]{},.<>/?;:'\"\\|`~"
    ).toCharArray();

    private static final int BIN = 2;
    private static final int OCT = 8;
    private static final int HEX = 16;

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PutMapping("/")
    public void convertValue(
            @RequestParam(value = "value") String value,
            @RequestParam(value = "fromBase") int fromBase,
            @RequestParam(value = "toBase") int toBase) {

        // To decimal at first => to others from it
        BigInteger decimal = toDecimal(value, fromBase);

        // Needed one
        String required = fromDecimal(decimal, toBase);

        // Additional info
        String binary = fromDecimal(decimal, BIN);
        String octal = fromDecimal(decimal, OCT);
        String hex = fromDecimal(decimal, HEX);
    }

    // Helper method for conversion

    private BigInteger toDecimal(String value, int fromBase) {

    }

    private String fromDecimal(BigInteger value, int toBase) {

    }

    private int valueOfChar(char c) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (DIGITS[i] == c) return i;
        }
        return -1;
    }
}
*/
