package com.zte.hxfirefox.decimal.big.integer.utils;

import org.junit.Test;

import static com.zte.hxfirefox.decimal.big.integer.utils.DigitCompare.*;
import static com.zte.hxfirefox.decimal.big.integer.utils.StringDigitUtils.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StringDigitUtilsTest {
    @Test
    public void should_get_origin_string_when_string_without_minus_sign() throws Exception {
        //given
        String number = "24";
        //when
        final String absNumber = absString(number);
        //then
        assertThat(number.equals(absNumber), is(true));
    }

    @Test
    public void should_get_abs_string_when_string_with_minus_sign() throws Exception {
        //given
        String number = "-24";
        //when
        final String absNumber = absString(number);
        //then
        assertThat(number.split("-")[1].equals(absNumber), is(true));
    }

    @Test
    public void should_get_format_string() throws Exception {
        //given
        String number = "24";
        String expectedNumber = "0024";
        //when
        final String formatNumber = formatNumber(number, 4);
        //then
        assertThat(expectedNumber.equals(formatNumber), is(true));
    }

    @Test
    public void should_get_string_trim_zero() throws Exception {
        //given
        String number = "0024";
        String expectedNumber = "24";
        //when
        final String formatNumber = trimHeadZero(number);
        //then
        assertThat(expectedNumber.equals(formatNumber), is(true));
    }

    @Test
    public void should_longer_string_larger_than_short_one() throws Exception {
        //given
        String src = "1234";
        String target = "12";
        //when
        final DigitCompare result = compare(src, target);
        //then
        assertThat(result, is(GT));
    }

    @Test
    public void should_shorter_string_larger_than_long_one() throws Exception {
        //given
        String src = "12";
        String target = "1234";
        //when
        final DigitCompare result = compare(src, target);
        //then
        assertThat(result, is(LT));
    }

    @Test
    public void should_equal_when_string_lenght_and_content_same() throws Exception {
        //given
        String src = "1234";
        String target = "1234";
        //when
        final DigitCompare result = compare(src, target);
        //then
        assertThat(result, is(EQ));
    }

    @Test
    public void should_larger_when_same_lenght_string_with_different_content() throws Exception {
        //given
        String src = "1244";
        String target = "1234";
        //when
        final DigitCompare result = compare(src, target);
        //then
        assertThat(result, is(GT));
    }

    @Test
    public void should_smaller_when_same_lenght_string_with_different_content() throws Exception {
        //given
        String src = "1224";
        String target = "1234";
        //when
        final DigitCompare result = compare(src, target);
        //then
        assertThat(result, is(LT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_compare_with_negative() throws Exception {
        //given
        String src = "-1234";
        String targer = "1234";
        //when
        compare(src, targer);
        //then
    }
}