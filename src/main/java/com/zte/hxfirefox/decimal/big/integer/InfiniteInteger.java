package com.zte.hxfirefox.decimal.big.integer;

import com.google.common.collect.Lists;

import java.util.List;

import static java.lang.Math.max;

/**
 * Created by 黄翔 on 15-3-7.
 */
public class InfiniteInteger {
    private static final int DIRECT_CALC_NUM_SIZE = 4;
    public static final int DIGIT_NEED_CARRY = 9;
    public static final String INFINITE_INTEGER_ZERO = "0";
    public static final String TRIM_ZERO_PATTERN = "^0+";

    public static String mul(final String x, final String y) {

        final String signOfString = getSignOfString(x, y);

        final String absX = absString(x);
        final String absY = absString(y);
        int maxLen = max(absX.length(), absY.length());

        if (canMulDirect(maxLen)) {
            return new StringBuilder()
                    .append(signOfString)
                    .append(mulDirect(absX, absY))
                    .toString();
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


        String[] sBD = getOriginAndCarry(mul(b, d), getSecondHalfOfString(maxLen));
        final String origin4BD = sBD[0];
        final String carry4BD = sBD[1];

        String[] sADBC = getOriginAndCarry(getValueOfADBC(mul(a, d), mul(b, c), carry4BD),
                max(getFirstHalfOfString(maxLen), getSecondHalfOfString(maxLen)));
        final String origin4ADBC = sADBC[0];
        final String carry4ADBC = sADBC[1];

        return new StringBuilder()
                .append(signOfString)
                .append(trimHeadZero(new StringBuilder()
                        .append(add(mul(a, c), carry4ADBC))
                        .append(origin4ADBC)
                        .append(origin4BD)
                        .toString()))
                .toString();
    }

    private static boolean canMulDirect(int maxLen) {
        return maxLen <= DIRECT_CALC_NUM_SIZE;
    }

    private static String absString(String number) {
        return number.replaceAll("^-", "");
    }

    private static String getSignOfString(String x, String y) {
        return (x.startsWith("-") || y.startsWith("-")) ? "-" : "";
    }

    private static int getFirstHalfOfString(int maxLen) {
        return maxLen / 2;
    }

    private static int getSecondHalfOfString(int maxLen) {
        return maxLen - getFirstHalfOfString(maxLen);
    }

    private static String getValueOfADBC(String x, String y, String carry) {
        return !INFINITE_INTEGER_ZERO.equals(carry) ? add(add(x, y), carry) : add(x, y);
    }

    private static Integer mulDirect(String x, String y) {
        return Integer.parseInt(x) * Integer.parseInt(y);
    }

    private static String formatNumber(String number, int len) {
        while (len > number.length()) {
            number = new StringBuilder().append("0").append(number).toString();
        }
        return number;
    }

    private static String[] getOriginAndCarry(String number, int len) {
        String[] originAndCarry = {number, INFINITE_INTEGER_ZERO};

        if (len < number.length()) {
            int t = number.length() - len;
            originAndCarry[0] = number.substring(t);
            originAndCarry[1] = number.substring(0, t);
        } else {
            String origin = originAndCarry[0];
            for (int i = origin.length(); i < len; i++) {
                origin = INFINITE_INTEGER_ZERO + origin;
            }
            originAndCarry[0] = origin;
        }
        return originAndCarry;
    }

    public static String add(String x, String y) {
        String str = "";
        if (x.startsWith("-") && !y.startsWith("-")) {
            return sub(y, x.replaceAll("^-", ""));
        } else if (!x.startsWith("-") && y.startsWith("-")) {
            return sub(x, y.replaceAll("^-", ""));
        } else if (x.startsWith("-") && y.startsWith("-")) {
            return "-" + add(x.replaceAll("^-", ""), y.replaceAll("^-", ""));
        }

        int maxLen = Math.max(x.length(), y.length());
        x = formatNumber(x, maxLen);
        y = formatNumber(y, maxLen);
        int flag = 0;
        for (int i = maxLen - 1; i >= 0; i--) {
            int t =
                    flag + Integer.parseInt(x.substring(i, i + 1))
                            + Integer.parseInt(y.substring(i, i + 1));
            if (t > DIGIT_NEED_CARRY) {
                flag = 1;
                t = t - 10;
            } else {
                flag = 0;
            }
            str = new StringBuilder().append("").append(t).append(str).toString();
        }
        if (flag != 0) {
            str = new StringBuilder().append("").append(flag).append(str).toString();
        }

        return new StringBuilder().append(trimHeadZero(str)).toString();
    }

    private static String trimHeadZero(String number) {
        return number.replaceAll(TRIM_ZERO_PATTERN, "");
    }

    public static String sub(String x, String y) {

        // x是正数，y也是正数
        if (x.startsWith("-") && y.startsWith("-")) {
            return sub(y.replaceAll("^-", ""), x.replaceAll("^-", ""));
        } else if (x.startsWith("-") && !y.startsWith("-")) {
            return "-" + add(x.replaceAll("^-", ""), y);
        } else if (!x.startsWith("-") && y.startsWith("-")) {
            return add(x, y.replaceAll("^-", ""));
        }
        int flag = checkBigger(x, y);

        if (flag == 0) {
            return "0";
        } else if (flag == -1) {
            String tmp = y;
            y = x;
            x = tmp;
        }
        y = formatNumber(y, x.length());

        int[] difference = new int[x.length()];

        for (int i = x.length() - 1; i >= 0; i--) {

            int tmpdifference;

            tmpdifference = Integer.parseInt(x.charAt(i) + "")
                    - Integer.parseInt(y.charAt(i) + "") + difference[i];

            if (tmpdifference < 0) {

                tmpdifference += 10;
                difference[i - 1] = -1;// 表示进位
            }

            difference[i] = tmpdifference;
        }

        StringBuilder returnvalue = new StringBuilder();

        for (int i : difference) {
            returnvalue.append(i);
        }

        String rv = trimHeadZero(returnvalue.toString());

        if ("".equals(rv)) {
            return "0";
        }

        if (flag == -1) {
            rv = "-" + rv;
        }

        return rv;
    }

    private static int checkBigger(String x, String y) {

        if (x.length() > y.length()) {

            return 1;

        } else if (x.length() < y.length()) {

            return -1;

        } else {

            for (int i = 0; i < x.length(); i++) {

                if (x.charAt(i) > y.charAt(i)) {

                    return 1;

                } else if (x.charAt(i) < y.charAt(i)) {
                    return -1;
                }
            }

            return 0;
        }
    }

    private static List<String> getArgument(String number, int len) {
        String formatX = formatNumber(number, len);

        List<String> arg = Lists.newArrayList();
        arg.add(formatX.substring(0, getFirstHalfOfString(len)));
        arg.add(formatX.substring(getFirstHalfOfString(len)));
        return arg;
    }
}
