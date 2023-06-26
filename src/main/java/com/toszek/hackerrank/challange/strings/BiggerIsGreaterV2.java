package com.toszek.hackerrank.challange.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * https://www.hackerrank.com/challenges/bigger-is-greater/problem
 * Lexicographical order is often known as alphabetical order when dealing with strings. A string is greater than another string if it comes later in a lexicographically sorted list.
 * Given a word, create a new word by swapping some or all of its characters. This new word must meet two criteria:
 * It must be greater than the original word
 * It must be the smallest word that meets the first condition
 */
public class BiggerIsGreaterV2 {
    public static final String NO_ANSWER = "no answer";

    public static String biggerIsGreater(String w) {
        if (w.length() == 1) return NO_ANSWER;

        int replacedCharIndex = 0;
        Set<String> result = new TreeSet<>();
        result.add(w);
        while (replacedCharIndex < w.length() - 1) {
            Set<String> temp = new TreeSet<>();
            for (String word : result) {
                for (int i = replacedCharIndex; i < w.length(); i++) {
                    String newWord = replaceAndGet(word, i, replacedCharIndex);
                    if (!isSmaller(w, newWord, replacedCharIndex)) {
                        temp.add(newWord);
                    }
                }
            }
            result = temp;
            replacedCharIndex++;
        }

        return result.stream()
                .filter(word -> word.compareTo(w) > 0)
                .findFirst().orElse(NO_ANSWER);
    }

    private static boolean isSmaller(final String word, final String newWord, int upToIndex) {
        int diff;
        for (int i = 0; i <= upToIndex; i++) {
            diff = newWord.charAt(i) - word.charAt(i);
            if (diff > 0) {
                return false;
            } else if (diff < 0) {
                return true;
            }
        }
        return false;
    }

    private static String replaceAndGet(final String word, final int i, final int k) {
        StringBuilder sb = new StringBuilder(word);
        sb.setCharAt(i, word.charAt(k));
        sb.setCharAt(k, word.charAt(i));
        return sb.toString();
    }
}
