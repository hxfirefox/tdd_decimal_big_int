package com.zte.hxfirefox.decimal.big.integer.expression;

import com.zte.hxfirefox.decimal.big.integer.infinite.InfiniteInteger;

public class CustomMinus implements CustomOperator {
    @Override
    public int getPrority() {
        return 1;
    }

    @Override
    public String compute(String sourceNumber, String targetNumber) {
        return InfiniteInteger.sub(sourceNumber, targetNumber);
    }
}
