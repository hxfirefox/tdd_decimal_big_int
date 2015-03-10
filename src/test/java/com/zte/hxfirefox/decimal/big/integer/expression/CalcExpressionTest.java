package com.zte.hxfirefox.decimal.big.integer.expression;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalcExpressionTest {

    private CalcExpression calcExp;

    @Before
    public void setUp() throws Exception {
        calcExp = new CalcExpression();
    }
    
    @Test
    public void should_calculate_non_infinite_integer() throws Exception {
        //given
        final String expssion = "234 + -12 * -34 * 56 + 4578 - -234 - 3456";
        final String expect = "24438";
        //when
        final String result = calcExp.calculate(expssion);
        //then
        assertThat(expect.equals(result), is(true));
    }    
    
    @Test
    public void should_calculate_infinite_integer() throws Exception {
        //given
        final String expssion = "-8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888 + " +
                "-999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999" +
                "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 * " +
                "88888888888888888888888888 + " +
                "777777777777777777777777777777777777777777777777 + -33333333333333333333333333333333333333333333333333333333333333";
        final String expect = "-888888888888888888888888880000000000000000000000000000000000000000000000000000000000000000000" +
                "0000000000000000000000000088888888888888888888888888888888888888888889222222222222214444444444444" +
                "44444444355555555555555555555555556";
        //when
        final String result = calcExp.calculate(expssion);
        //then
        assertThat(expect.equals(result), is(true));
    }
}
