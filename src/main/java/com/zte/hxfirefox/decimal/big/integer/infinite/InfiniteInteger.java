package com.zte.hxfirefox.decimal.big.integer.infinite;


import com.zte.hxfirefox.decimal.big.integer.utils.DigitCompare;

import java.util.List;

import static com.zte.hxfirefox.decimal.big.integer.algorithm.DivideAndConqueAlgorithm.*;
import static com.zte.hxfirefox.decimal.big.integer.infinite.DigitCalc.*;
import static com.zte.hxfirefox.decimal.big.integer.utils.DigitCompare.*;
import static com.zte.hxfirefox.decimal.big.integer.utils.StringDigitUtils.*;
import static java.lang.Math.max;


public class InfiniteInteger {
    private static final int DIGIT_NEED_CARRY = 9;
    private static final String INFINITE_INTEGER_ZERO = "0";
    private static final String NEGATIVE_SIGN = "-";
    private static final int NUMBER_BASE = 10;
    private static final int POSITIVE_CARRY = 1;
    private static final int ZERO_CARRY = 0;
    private static final String EMPTY = "";

    public static String mul(final String sourceNumber, final String targetNumber) {

        final String signOfString = getSign4Mul(sourceNumber, targetNumber);

        final String absX = absString(sourceNumber);
        final String absY = absString(targetNumber);
        int maxLen = max(absX.length(), absY.length());

        if (canMulDirect(maxLen)) {
            return String.format("%s%d", signOfString, mulDigit(absX, absY));
        }

        // A(x) = a * x^m +b
        // B(x) = c * x^m +d
        // A(x) * B(x) = ac * x^2m + (ad + bc) * x^m + bd
        List<String> argAB = getArgument(absX, maxLen);
        String a = argAB.get(0);
        String b = argAB.get(1);

        List<String> argCD = getArgument(absY, maxLen);
        String c = argCD.get(0);
        String d = argCD.get(1);


        String[] resultBD = getOriginAndCarry(mul(b, d), getSecondHalf(maxLen));
        final String origin4BD = resultBD[0];
        final String carry4BD = resultBD[1];

        String[] resultADBC = getOriginAndCarry(calcADBC(mul(a, d), mul(b, c), carry4BD),
                max(getFirstHalf(maxLen), getSecondHalf(maxLen)));
        final String origin4ADBC = resultADBC[0];
        final String carry4ADBC = resultADBC[1];

        return String.format("%s%s", 
                signOfString, 
                trimHeadZero(String.format("%s%s%s", 
                        add(mul(a, c), carry4ADBC), 
                        origin4ADBC, origin4BD)));
    }

    public static String add(String sourceNumber, String targetNumber) {
        if (isOnlyOneNegative(sourceNumber, targetNumber)) {
            return sub(targetNumber, absString(sourceNumber));
        } 
        
        if (isOnlyOneNegative(targetNumber, sourceNumber)) {
            return sub(sourceNumber, absString(targetNumber));
        }
        
        if (isBothNegative(sourceNumber, targetNumber)) {
            return NEGATIVE_SIGN + add(absString(sourceNumber), absString(targetNumber));
        }

        return addOnlyWithPositive(sourceNumber, targetNumber);
    }

    public static String sub(String sourceNumber, String targetNumber) {

        if (isBothNegative(sourceNumber, targetNumber)) {
            return sub(absString(targetNumber), absString(sourceNumber));
        }

        if (isOnlyOneNegative(sourceNumber, targetNumber)) {
            return NEGATIVE_SIGN + add(absString(sourceNumber), targetNumber);
        }

        if (isOnlyOneNegative(targetNumber, sourceNumber)) {
            return add(sourceNumber, absString(targetNumber));
        }
        return subOnlyWithPositive(sourceNumber, targetNumber);
    }

    private static String addOnlyWithPositive(String sourceNumber, String targetNumber) {

        int maxLen = Math.max(sourceNumber.length(), targetNumber.length());
        sourceNumber = formatNumber(sourceNumber, maxLen);
        targetNumber = formatNumber(targetNumber, maxLen);

        String result = "";
        int carry = ZERO_CARRY;
        for (int indexOfString = maxLen - 1; indexOfString >= 0; indexOfString--) {
            int digit = calcAddDigitFromString(sourceNumber, targetNumber, carry, indexOfString);
            if (digit > DIGIT_NEED_CARRY) {
                carry = POSITIVE_CARRY;
                digit -= NUMBER_BASE;
            } else {
                carry = ZERO_CARRY;
            }
            result = generateResultWithDigitCarry(result, digit);
        }

        return trimHeadZero(carry != ZERO_CARRY ? 
                generateResultWithDigitCarry(result, carry) : result);
    }

    private static String subOnlyWithPositive(String sourceNumber, String targetNumber) {
        DigitCompare digitCompare = compare(sourceNumber, targetNumber);

        if (digitCompare == EQ) {
            return INFINITE_INTEGER_ZERO;
        } else if (digitCompare == LT) {
            String tmp = targetNumber;
            targetNumber = sourceNumber;
            sourceNumber = tmp;
        }
        return subWithOrder(sourceNumber, targetNumber, digitCompare);
    }

    private static String subWithOrder(String sourceNumber, 
                                       String targetNumber, DigitCompare compare) {

        String result = trimHeadZero(subBaseDigit(sourceNumber, 
                formatNumber(targetNumber, sourceNumber.length())));
        return EMPTY.equals(result) ? INFINITE_INTEGER_ZERO :
                (compare == LT ? NEGATIVE_SIGN : "") + result;
    }

    private static String calcADBC(String sourceNumber, String targetNumber, String carry) {
        return add(add(sourceNumber, targetNumber), carry);
    }

    private static String getSign4Mul(String x, String y) {
        return (x.startsWith(NEGATIVE_SIGN) || y.startsWith(NEGATIVE_SIGN)) ? NEGATIVE_SIGN : "";
    }

    public static boolean isBothNegative(String sourceNumber, String targetNumber) {
        return sourceNumber.startsWith(NEGATIVE_SIGN) && targetNumber.startsWith(NEGATIVE_SIGN);
    }

    public static boolean isOnlyOneNegative(String mayNegative, String mayPositive) {
        return mayNegative.startsWith(NEGATIVE_SIGN) && !mayPositive.startsWith(NEGATIVE_SIGN);
    }
}
