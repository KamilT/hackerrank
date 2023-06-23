package com.toszek.hackerrank.challange.strings;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  given phone numbers, generate possible strings
 * 0 - 0,
 * 1 - 1
 * 2 - abc
 * 3 - def
 * 4 - ghi
 * 5 - jkl
 * 6 - mno
 * 7 - prs
 * 8 - tuw
 * 9 - xyz
 */
class PhoneNumbersTest {

    @Test
    void generates_output_for_12() {
        assertThat(PhoneNumbers.phoneNumbers("12")).containsExactlyInAnyOrder(
                "1a","1b","1c"
        );
    }

    @Test
    void generates_output_for_123() {
        assertThat(PhoneNumbers.phoneNumbers("123")).containsExactlyInAnyOrder(
                "1ad","1bd","1cd", "1ae","1be","1ce", "1af","1bf","1cf"
        );
    }
}
