package com.zte.hxfirefox.decimal.big.integer.expression;

import com.zte.hxfirefox.decimal.big.integer.infinite.InfiniteInteger;

public class CustomMultiply implements CustomOperator {
    @Override
    public int getPrority() {
        return 2;
    }

    @Override
    public String compute(String sourceNumber, String targetNumber) {
        return InfiniteInteger.mul(sourceNumber, targetNumber);
    }
}
