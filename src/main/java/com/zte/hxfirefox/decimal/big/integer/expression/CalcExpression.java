package com.zte.hxfirefox.decimal.big.integer.expression;

import com.google.common.collect.Lists;
import com.zte.hxfirefox.decimal.big.integer.utils.ValidateNumberUtils;

import java.util.*;

import static com.zte.hxfirefox.decimal.big.integer.infinite.InfiniteInteger.*;

public class CalcExpression {
    public static final String STACK_END = "#";
    private List<String> numberAndOperator = Lists.newArrayList();
    private final Stack<String> numStack = new Stack<>();
    private final Stack<String> opStack = new Stack<>();
    private String currentOperator;
    private String opStackTop;
    private int i;

    public String calculate(String expression) {
        cleanStack();
        final String[] exp = expValidate(expression);
        for (; i < exp.length; i++) {
            if (isOperator(exp[i])) {
                processOperator(exp[i]);
            } else {
                processOperand(exp[i]);
            }
        }

        return null;
    }

    private void processOperator(final String currentOperator) {
        this.currentOperator = currentOperator;
        this.opStackTop = opStack.peek();
        char calMode = CALCULATE_MODE.getRule(currentOperator, opStackTop);
        switch (calMode) {
            case '>':
                processStackHigerPriorityOperator();
                break;
            case '<':
                processStackLowerPriorityOperator();
                break;
            case '=':
                processStackEqualPriorityOperator();
                break;
            default:
                break;
        }
    }

    private void processStackLowerPriorityOperator() {
        opStack.push(currentOperator);
    }

    private void processStackHigerPriorityOperator() {
        numStack.push(CALCULATE.exec(opStack.pop(), numStack.pop(),
                numStack.pop()));
        --i; // pointer back to the previous operator.
    }

    private void processStackEqualPriorityOperator() {
        if (TERMINATE_TOKENS.START_END_MARK == currentOperator) {
            System.out.println(numStack.peek());
        } else if (")" == currentOperator) {
            opStack.pop();
        }
    }

    private void processOperand(final String operand) {
        numStack.push(operand);
    }

    private String[] expValidate(String expression) {
        if (expression.isEmpty() || expression == null) {
            throw new NullPointerException("you should give an expression!");
        }

        final String[] exp = expression.split(" ");
        for (String s : exp) {
            if (!isOperator(s)) {
                ValidateNumberUtils.numberValidation(s);
            }
        }
        return exp;
    }

    private void cleanStack() {
        numStack.clear();
        opStack.clear();
        opStack.push(STACK_END);
    }

    private boolean isOperator(String s) {
        return "+".equals(s) || "-".equals(s) || "*".equals(s);
    }

    enum CALCULATE {
        INSTANCE;

        public static String exec(final String operator, final String right,
                                  final String left) {
            switch (operator) {
                case "+":
                    return add(left, right);
                case "-":
                    return sub(left,right);
                case "*":
                    return mul(left,right);
//                case '/':
//                    return left / right;
                default:
                    throw new IllegalArgumentException("Unsupported operator: "
                            + operator);
            }
        }
    }

    enum CALCULATE_MODE {
        INSTANCE;

        private static char[][] RULES = {
                // + - * / ( ) #
                { '>', '>', '<', '<', '<', '>', '>' }, // +
                { '>', '>', '<', '<', '<', '>', '>' }, // -
                { '>', '>', '>', '>', '<', '>', '>' }, // *
                { '>', '>', '>', '>', '<', '>', '>' }, // /
                { '<', '<', '<', '<', '<', '=', 'o' }, // (
                { '>', '>', '>', '>', 'o', '>', '>' }, // )
                { '<', '<', '<', '<', '<', 'o', '=' }, // #
        };

        static {
            if (RULES.length != TERMINATE_TOKENS.getTokenSize() || RULES.length < 1
                    || RULES[0].length != TERMINATE_TOKENS.getTokenSize()) {
                throw new IllegalArgumentException("Rules matrix is incorrect!");
            }
        }

        public static char getRule(final String currentOperator, final String opStackTop) {
            try {
                return RULES[TERMINATE_TOKENS.getTokenId(opStackTop)][TERMINATE_TOKENS
                        .getTokenId(currentOperator)];
            } catch (Throwable e) {
                throw new RuntimeException("No rules were defined for some token!");
            }
        }
    }

    enum TERMINATE_TOKENS {
        INSTANCE;

        public static final String START_END_MARK = "#";
        private static final Map<String, Integer> TOKENs = new HashMap<>();

        static {
            // token, token id
            TOKENs.put("+", 0);
            TOKENs.put("-", 1);
            TOKENs.put("*", 2);
            TOKENs.put("/", 3);
            TOKENs.put("(", 4);
            TOKENs.put(")", 5);
            TOKENs.put(START_END_MARK, 6);
        }

        private static Set<String> NEGATIVE_NUM_SENSITIVE = new HashSet<>();

        public static synchronized Set<String> getNegativeNumSensitiveToken() {
            if (NEGATIVE_NUM_SENSITIVE.size() == 0) {
                NEGATIVE_NUM_SENSITIVE.addAll(TOKENs.keySet());
                NEGATIVE_NUM_SENSITIVE.remove(')');
            }
            return NEGATIVE_NUM_SENSITIVE;
        }

        public static boolean isTerminateToken(final String token) {
            Set<String> keys = TOKENs.keySet();
            return keys.contains(token);
        }

        public static int getTokenId(final String token) {
            return TOKENs.get(token) == null ? -1 : TOKENs.get(token);
        }

        public static int getTokenSize() {
            return TOKENs.size();
        }

    }
}
