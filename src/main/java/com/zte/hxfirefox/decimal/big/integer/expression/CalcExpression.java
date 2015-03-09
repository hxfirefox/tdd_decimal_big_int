package com.zte.hxfirefox.decimal.big.integer.expression;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class CalcExpression {
    private final String exp;
    private List<String> numberAndOperator = Lists.newArrayList();

    public CalcExpression(final String exp) {
        this.exp = exp;
    }

    public String getResult() {
        Collections.addAll(numberAndOperator, exp.split(" "));
        return calculate(numberAndOperator);
    }

    private String calculate(List<String> numberAndOperator) {
        for (String s : numberAndOperator) {

        }
        return null;
    }
}
