package com.zte.hxfirefox.decimal.big.integer.infinite;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DigitCalcTest {

    @Test
    public void should_add_digit_from_string() throws Exception {
        //given
        String number1 = "1234";
        String number2 = "2345";
        //when
        final int result = DigitCalc.calcAddDigitFromString(number1, number2, 1, 2);
        //then
        assertThat(result, is(8));
    }

    @Test
    public void should_mul_digit() throws Exception {
        //given

        //when
        final int result = DigitCalc.mulDigit("12", "13");
        //then
        assertThat(result, is(156));
    }

    @Test
    public void should_multiply_when_string_length_greater_than_direct_calc_size() throws Exception {
        //given

        //when
        final boolean can = DigitCalc.canMulDirect(5);
        //then
        assertThat(can, is(false));
    }

    @Test
    public void should_multiply_when_string_length_less_than_direct_calc_size() throws Exception {
        //given

        //when
        final boolean can = DigitCalc.canMulDirect(2);
        //then
        assertThat(can, is(true));
    }

    @Test
    public void should_multiply_when_string_length_equal_direct_calc_size() throws Exception {
        //given

        //when
        final boolean can = DigitCalc.canMulDirect(4);
        //then
        assertThat(can, is(true));
    }

    @Test
    public void should_sub_base_digit() throws Exception {
        //given
        String expect = "4198";
        //when
        final String result = DigitCalc.subBaseDigit("5432", "1234");
        //then
        assertThat(expect.equals(result), is(true));
    }

    @Test
    public void should_generate_result_with_digit_carry() throws Exception {
        //given
        String expect = "1234";
        //when
        final String result = DigitCalc.generateResultWithDigitCarry("234", 1);
        //then
        assertThat(expect.equals(result), is(true));
    }
}