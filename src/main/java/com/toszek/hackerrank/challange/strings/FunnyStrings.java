package com.toszek.hackerrank.challange.strings;

/**
 * https://www.hackerrank.com/challenges/funny-string/problem
 * In this challenge, you will determine whether a string is funny or not. To determine whether a string is funny, create a copy of the string in reverse e.g. . Iterating through each string, compare the absolute difference in the ascii values of the characters at positions 0 and 1, 1 and 2 and so on to the end. If the list of absolute differences is the same for both strings, they are funny.
 * Determine whether a give string is funny. If it is, return Funny, otherwise return Not Funny.
 */
public class FunnyStrings {
    public static String funnyString(String s) {
        if(s == null || s.length() < 2) return "Not Funny";

        final char[] characters = s.toCharArray();
        int startIndex = 0;
        int endIndex = characters.length -1;
        while(startIndex < s.length() - 1) {
            int difOriginal = calculateDifference(characters[startIndex], characters[startIndex +1]);
            int difReversed = calculateDifference(characters[endIndex], characters[endIndex -1]);

            if(difOriginal != difReversed) {
                return "Not Funny";
            }
            startIndex += 1;
            endIndex -= 1;
        }
        return "Funny";
    }

    private static int calculateDifference(final char character1, final char character2) {
        return Math.abs(character1 - character2);
    }
}
