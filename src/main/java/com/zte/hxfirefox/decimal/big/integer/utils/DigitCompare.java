package com.zte.hxfirefox.decimal.big.integer.utils;

public enum DigitCompare {
    EQ(0), 
    GT(1), 
    LT(-1);
    
    private final int compareResult;

    DigitCompare(int compareResult) {
        this.compareResult = compareResult;
    }
}
