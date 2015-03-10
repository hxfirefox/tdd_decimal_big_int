package com.zte.hxfirefox.decimal.big.integer.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.zte.hxfirefox.decimal.big.integer.utils.ValidateNumberUtils.*;

public class CalcExpression {

    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String SPLIT = " ";

    private String[] expressionValidate(String expression) {
        if (expression.isEmpty()) {
            throw new NullPointerException("you should give an expression!");
        }

        final String[] exp = expression.split(SPLIT);
        for (String s : exp) {
            if (!isOperator(s)) {
                numberValidation(s);
            }
        }

        return exp;
    }

    private boolean isOperator(String s) {
        return PLUS.equals(s) || MINUS.equals(s) || MULTIPLY.equals(s);
    }

    private void compute(Stack<String> numStack, Stack<CustomOperator> operStack) {
        String targetNumber = numStack.pop();
        String sourceNumber = numStack.pop();
        numStack.push(operStack.pop().compute(sourceNumber, targetNumber));
    }

    public String calculate(String expression) {
        return calculate(expression, new Stack<String>(), new Stack<CustomOperator>());
    }

    private String calculate(String expression, Stack<String> numStack, Stack<CustomOperator> operStack) {
        final String[] exp = expressionValidate(expression);
        for (String s : exp) {
            if (isOperator(s)) {
                CustomOperator currentOper = getComputeOper().get(s);
                while (!operStack.empty() && operStack.peek().getPrority() >= currentOper.getPrority()) {
                    compute(numStack, operStack);
                }
                operStack.push(currentOper);
            } else {
                numStack.push(s);
            }
        }

        while (!operStack.empty()) {
            compute(numStack, operStack);
        }
        return numStack.pop();
    }

    private Map<String, CustomOperator> getComputeOper() {

        return new HashMap<String, CustomOperator>() {
            private static final long serialVersionUID = 7706718608122369958L;
            {
                put(PLUS, new CustomPlus());
                put(MINUS, new CustomMinus());
                put(MULTIPLY, new CustomMultiply());
            }
        };
    }
}
