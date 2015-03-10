package com.zte.hxfirefox.decimal.big.integer.expression;

public interface CustomOperator {
    public int getPrority();
    public String compute(String sourceNumber, String targetNumber);
}
