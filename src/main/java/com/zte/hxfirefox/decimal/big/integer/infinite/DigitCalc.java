package com.zte.hxfirefox.decimal.big.integer.infinite;

import static java.lang.Integer.parseInt;

public class DigitCalc {
    private static final int DIRECT_CALC_NUM_SIZE = 4;
    private static final int NUMBER_BASE = 10;
    private static final int NEGATIVE_CARRY = -1;
    private static final int ZERO_CARRY = 0;

    public static int calcAddDigitFromString(
            String sourceNumber, 
            String targetNumber, 
            int carry, 
            int indexOfString) {
        return parseInt(getOneCharacter(sourceNumber, indexOfString)) +
                parseInt(getOneCharacter(targetNumber, indexOfString)) +
                carry;
    }

    private static int calcSubDigitFromString(
            String sourceNumber, 
            String targetNumber, 
            int carry, 
            int indexOfString) {
        return parseInt(getOneCharacter(sourceNumber, indexOfString)) -
                parseInt(getOneCharacter(targetNumber, indexOfString)) +
                carry;
    }

    public static Integer mulDigit(String sourceNumber, String targetNumber) {
        return parseInt(sourceNumber) * parseInt(targetNumber);
    }

    private static String getOneCharacter(String number, int indexOfString) {
        return String.format("%s", number.charAt(indexOfString));
    }

    public static boolean canMulDirect(int maxLen) {
        return maxLen <= DIRECT_CALC_NUM_SIZE;
    }

    public static String subBaseDigit(String sourceNumber, String targetNumber) {
        int carry = ZERO_CARRY;
        String result = "";
        for (int indexOfString = sourceNumber.length() - 1; indexOfString >= 0; indexOfString--) {
            int digit = calcSubDigitFromString(sourceNumber, targetNumber, carry, indexOfString);

            if (digit < 0) {
                digit += NUMBER_BASE;
                carry = NEGATIVE_CARRY;
            } else {
                carry = ZERO_CARRY;
            }
            result = new StringBuilder().append(digit).append(result).toString();
        }
        return result;
    }

    public static String generateResultWithDigitCarry(String result, int carry) {
        return String.format("%d%s", carry, result);
    }
}
