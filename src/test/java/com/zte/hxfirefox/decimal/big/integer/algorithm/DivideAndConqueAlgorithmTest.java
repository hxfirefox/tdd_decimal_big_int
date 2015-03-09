package com.zte.hxfirefox.decimal.big.integer.algorithm;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DivideAndConqueAlgorithmTest {

    @Test
    public void should_get_argument() throws Exception {
        //given

        //when
        final List<String> argument = DivideAndConqueAlgorithm.getArgument("123", 3);
        //then
        assertThat("1".equals(argument.get(0)), is(true));
        assertThat("23".equals(argument.get(1)), is(true));
    }

    @Test
    public void should_get_origin_result_and_carry_equal_zero() throws Exception {
        //given

        //when
        final String[] originAndCarry = DivideAndConqueAlgorithm.getOriginAndCarry("123", 4);
        //then
        assertThat("0123".equals(originAndCarry[0]), is(true));
        assertThat("0".equals(originAndCarry[1]), is(true));

    }    
    
    @Test
    public void should_get_origin_result_and_carry() throws Exception {
        //given

        //when
        final String[] originAndCarry = DivideAndConqueAlgorithm.getOriginAndCarry("123", 2);
        //then
        assertThat("23".equals(originAndCarry[0]), is(true));
        assertThat("1".equals(originAndCarry[1]), is(true));

    }
}