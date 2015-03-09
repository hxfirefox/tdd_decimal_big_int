package com.zte.hxfirefox.decimal.big.integer.utils;

import static com.zte.hxfirefox.decimal.big.integer.utils.DigitCompare.*;

public class StringDigitUtils {
    private static final String REGEX_NEGATIVE = "^-";
    private static final String REXEX_ZERO = "^0+";
    public static final String ZERO = "0";
    private static final String MINUS = "-";

    public static String absString(String number) {
        return number.replaceAll(REGEX_NEGATIVE, "");
    }

    public static String trimHeadZero(String number) {
        return number.replaceAll(REXEX_ZERO, "");
    }

    public static String formatNumber(String number, int len) {
        while (len > number.length()) {
            number = new StringBuilder().append(ZERO).append(number).toString();
        }
        return number;
    }

    private static DigitCompare compareDigit(String sourceNumber, String targetNumber) {
        for (int index = 0; index < sourceNumber.length(); index++) {
            if (sourceNumber.charAt(index) > targetNumber.charAt(index)) {
                return GT;
            } else if (sourceNumber.charAt(index) < targetNumber.charAt(index)) {
                return LT;
            }
        }
        return EQ;
    }

    public static DigitCompare compare(String sourceNumber, String targetNumber) {
        if (sourceNumber.startsWith(MINUS) || targetNumber.startsWith(MINUS)) {
            throw new IllegalArgumentException("only compare with two positive number");
        }

        if (sourceNumber.length() > targetNumber.length()) {
            return GT;
        } else if (sourceNumber.length() < targetNumber.length()) {
            return LT;
        } else {
            return compareDigit(sourceNumber, targetNumber);
        }
    }

}
