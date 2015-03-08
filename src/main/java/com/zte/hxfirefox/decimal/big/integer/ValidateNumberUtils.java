package com.zte.hxfirefox.decimal.big.integer;

import java.util.regex.Pattern;

/**
 * Created by 黄翔 on 15-3-8.
 */
public class ValidateNumberUtils {

    public static final String POSITIVE_NUMBER_PATTERN = "^[1-9]\\d*$";
    public static final String NEGATIVE_NUMBER_PATTERN = "-[1-9]\\d*$";
    public static final String ZERO = "0";

    private ValidateNumberUtils() {

    }

    public static String numberValidation(String number) {
        if (!matchPositiveNum(number) && !matchNegativeNum(number) && !matchZero(number)) {
            throw new IllegalArgumentException();
        }

        return number;
    }

    private static boolean matchZero(String number) {
        return ZERO.equals(number);
    }

    private static boolean matchNegativeNum(String number) {
        return Pattern.compile(NEGATIVE_NUMBER_PATTERN).matcher(number).matches();
    }

    private static boolean matchPositiveNum(String number) {
        return Pattern.compile(POSITIVE_NUMBER_PATTERN).matcher(number).matches();
    }
}
