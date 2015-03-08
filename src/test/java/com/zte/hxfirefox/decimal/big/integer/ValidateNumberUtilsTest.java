package com.zte.hxfirefox.decimal.big.integer;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateNumberUtilsTest {
    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_first_character_is_zero() throws Exception {
        // given
        final String number = "012";
        // when
        ValidateNumberUtils.numberValidation(number);
        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_first_character_behind_minus_is_zero() throws Exception {
        // given
        final String number = "-012";
        // when
        ValidateNumberUtils.numberValidation(number);
        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_contains_no_number_character() throws Exception {
        // given
        final String number = "12Ab#456";
        // when
        ValidateNumberUtils.numberValidation(number);
        // then
    }

    @Test
    public void should_return_zero() throws Exception {
        // given
        final String zero = "0";
        // when
        final String number = ValidateNumberUtils.numberValidation(zero);
        // then
        assertThat(zero.equals(number), is(true));
    }

    @Test
    public void should_return_positive_number() throws Exception {
        // given
        final String input = "9999999999999999999999999999999999999999" +
                "999999999999999999999999999999999999999999999999999" +
                "999999999999999999999999999999999999999999999999999" +
                "999999999999999999999999999999999999999999999999999999999";
        // when
        final String number = ValidateNumberUtils.numberValidation(input);
        // then
        assertThat(input.equals(number), is(true));
    }

    @Test
    public void should_return_negative_number() throws Exception {
        // given
        final String input = "-9999999999999999999999999999999999999999" +
                "999999999999999999999999999999999999999999999999999" +
                "999999999999999999999999999999999999999999999999999" +
                "999999999999999999999999999999999999999999999999999999999";
        // when
        final String number = ValidateNumberUtils.numberValidation(input);
        // then
        assertThat(input.equals(number), is(true));
    }
}