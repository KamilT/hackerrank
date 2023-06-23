package com.toszek.hackerrank.challange.strings;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * https://www.hackerrank.com/challenges/anagram/problem
 *
 * Two words are anagrams of one another if their letters can be rearranged to form the other word.
 * Given a string, split it into two contiguous substrings of equal length. Determine the minimum number of characters to change to make the two substrings into anagrams of one another.
 */
class AnagramTest {

    @Test
    void anagramOf_aaabbb() {
        assertThat(Anagram.anagram("aaabbb")).isEqualTo(3);
    }

    @Test
    void anagramOf_ab() {
        assertThat(Anagram.anagram("ab")).isEqualTo(1);
    }

    @Test
    void anagramOf_abc() {
        assertThat(Anagram.anagram("abc")).isEqualTo(-1);
    }

    @Test
    void anagramOf_mnop() {
        assertThat(Anagram.anagram("mnop")).isEqualTo(2);
    }

    @Test
    void anagramOf_xyyx() {
        assertThat(Anagram.anagram("xyyx")).isEqualTo(0);
    }

    @Test
    void anagramOf_xaxb_bbxx() {
        assertThat(Anagram.anagram("xaxbbbxx")).isEqualTo(1);
    }
}
