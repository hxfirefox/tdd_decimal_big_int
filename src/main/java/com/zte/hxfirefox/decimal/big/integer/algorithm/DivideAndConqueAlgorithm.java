package com.zte.hxfirefox.decimal.big.integer.algorithm;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static com.zte.hxfirefox.decimal.big.integer.utils.StringDigitUtils.formatNumber;

public class DivideAndConqueAlgorithm {
    private static final String ZERO = "0";

    public static int getFirstHalf(int maxLen) {
        return maxLen / 2;
    }

    public static int getSecondHalf(int maxLen) {
        return maxLen - getFirstHalf(maxLen);
    }

    public static String[] getOriginAndCarry(String number, int len) {
        String[] originAndCarry = {number, ZERO};

        if (len < number.length()) {
            int t = number.length() - len;
            originAndCarry[0] = number.substring(t);
            originAndCarry[1] = number.substring(0, t);
        } else {
            String origin = originAndCarry[0];
            for (int i = origin.length(); i < len; i++) {
                origin = ZERO + origin;
            }
            originAndCarry[0] = origin;
        }
        return originAndCarry;
    }

    public static List<String> getArgument(String number, int len) {
        String formatX = formatNumber(number, len);
        return of(formatX.substring(0, getFirstHalf(len)), formatX.substring(getFirstHalf(len)));
    }
}
