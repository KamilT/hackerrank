package com.toszek.hackerrank.challange.strings;

import java.util.ArrayList;
import java.util.List;

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
public class PhoneNumbers {
    public static final List<String> LETTERS = List.of("0", "1", "abc", "def", "ghi", "jkl", "mno", "prs", "tuw", "xyz");
    public static List<String> phoneNumbers(String number) {
        List<String> result = new ArrayList<>();
        findCombinationsRecursively(0, number, result, "");
        return result;
    }

    private static void findCombinationsRecursively(final int currentNumberIndex, final String number, final List<String> result, final String currentString) {
        // termination
        if (currentNumberIndex == number.length()) {
           result.add(currentString);
           return;
        }

        // get letters of current number
        final String lettersOfCurrentNumber = LETTERS.get(Character.getNumericValue(number.charAt(currentNumberIndex)));
        for (int i = 0; i < lettersOfCurrentNumber.length(); i++) {
             findCombinationsRecursively(currentNumberIndex + 1, number, result, currentString + lettersOfCurrentNumber.charAt(i));
        }
    }
}
