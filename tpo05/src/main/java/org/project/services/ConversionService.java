package org.project.services;

import org.project.exceptions.InvalidBaseException;
import org.project.exceptions.InvalidDigitException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ConversionService {
    private static final String PRINTABLE_CHARS =
                    "0123456789" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "!@#$%^&*()-_=+[]{}" +
                    ":;,.?/~`" +
                    "€£¥¢§©®µßøØæÆ";

    public String convertBase(String value, int fromBase, int toBase) {
        if (fromBase < 2 || fromBase > 100 || toBase < 2 || toBase > 100) {
            throw new InvalidBaseException("Base must be between 2 and 100. Received fromBase=" + fromBase + ", toBase=" + toBase);
        }

        BigInteger decimal = toDecimal(value, fromBase);
        return fromDecimal(decimal, toBase);
    }

    private BigInteger toDecimal(String value, int fromBase) {
        BigInteger result = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(fromBase);

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            int digit = PRINTABLE_CHARS.indexOf(c);
            if (digit < 0 || digit >= fromBase) {
                throw new InvalidDigitException("Invalid character '" + c + "' for base " + fromBase);
            }
            result = result.multiply(base).add(BigInteger.valueOf(digit));
        }

        return result;
    }

    private String fromDecimal(BigInteger number, int toBase) {
        if (number.equals(BigInteger.ZERO)) return "0";

        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(toBase);

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = number.divideAndRemainder(base);
            sb.append(PRINTABLE_CHARS.charAt(divRem[1].intValue()));
            number = divRem[0];
        }

        return sb.reverse().toString();
    }
}
