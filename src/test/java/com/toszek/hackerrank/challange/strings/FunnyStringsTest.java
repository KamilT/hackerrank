package com.toszek.hackerrank.challange.strings;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * https://www.hackerrank.com/challenges/funny-string/problem
 * In this challenge, you will determine whether a string is funny or not. To determine whether a string is funny, create a copy of the string in reverse e.g. . Iterating through each string, compare the absolute difference in the ascii values of the characters at positions 0 and 1, 1 and 2 and so on to the end. If the list of absolute differences is the same for both strings, they are funny.
 * Determine whether a give string is funny. If it is, return Funny, otherwise return Not Funny.
 */
class FunnyStringsTest {

    @Test
    void funnyStringIsFunny() {
        Assertions.assertThat(FunnyStrings.funnyString("acxz")).isEqualTo("Funny");
    }

    @Test
    void notFunnyStringIsNotFunny() {
        assertThat(FunnyStrings.funnyString("bcxz")).isEqualTo("Not Funny");
    }
}
