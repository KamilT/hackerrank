package com.toszek.hackerrank.challange.strings;

import java.util.Arrays;

/**
 * https://www.hackerrank.com/challenges/bigger-is-greater/problem
 * Lexicographical order is often known as alphabetical order when dealing with strings. A string is greater than another string if it comes later in a lexicographically sorted list.
 * Given a word, create a new word by swapping some or all of its characters. This new word must meet two criteria:
 * It must be greater than the original word
 * It must be the smallest word that meets the first condition
 * <p>
 * Convert the input string to a character array to make it mutable.
 * Start iterating from the rightmost character of the string.
 * Find the first character that is smaller than its next character in the array. Let's call this character pivot.
 * If no such character is found, it means the string is already the largest possible permutation. Return the input string as it is.
 * Find the smallest character greater than pivot on its right side in the array. Let's call this character swap.
 * Swap pivot with swap.
 * Sort the characters to the right of pivot in ascending order.
 * Reconstruct the resulting string from the character array.
 * Return the modified string as the output.
 */
public class BiggerIsGreaterV2 {
    public static final String NO_ANSWER = "no answer";

    public static String biggerIsGreater(String w) {
        if (w.length() == 1) return NO_ANSWER;

        char[] chars = w.toCharArray();

        // Find the pivot character
        int pivotIndex = -1;
        for (int i = chars.length - 2; i >= 0; i--) {
            if (chars[i] < chars[i + 1]) {
                pivotIndex = i;
                break;
            }
        }

        // If no pivot found, the string is already the largest permutation
        if (pivotIndex == -1) {
            return NO_ANSWER;
        }

        // Find the smallest character greater than pivot on its right side
        int swapIndex = -1;
        for (int i = chars.length - 1; i > pivotIndex; i--) {
            if (chars[i] > chars[pivotIndex]) {
                swapIndex = i;
                break;
            }
        }

        // Swap pivot and swap characters
        char temp = chars[pivotIndex];
        chars[pivotIndex] = chars[swapIndex];
        chars[swapIndex] = temp;

        // Sort the characters to the right of pivot in ascending order
        Arrays.sort(chars, pivotIndex + 1, chars.length);

        // Reconstruct the resulting string
        return new String(chars);
    }
}
