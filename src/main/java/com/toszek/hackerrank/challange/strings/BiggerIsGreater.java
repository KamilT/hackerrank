package com.toszek.hackerrank.challange.strings;

import java.util.Optional;

/**
 * https://www.hackerrank.com/challenges/bigger-is-greater/problem
 * Lexicographical order is often known as alphabetical order when dealing with strings. A string is greater than another string if it comes later in a lexicographically sorted list.
 * Given a word, create a new word by swapping some or all of its characters. This new word must meet two criteria:
 * It must be greater than the original word
 * It must be the smallest word that meets the first condition
 */
public class BiggerIsGreater {
    public static final String NO_ANSWER = "no answer";
    private final String originalWord;
    private String result;
    private BiggerIsGreater(final String originalWord) {
        this.originalWord = originalWord;
    }
    public static String biggerIsGreater(String w) {
        if(w.length() == 1) return NO_ANSWER;

        BiggerIsGreater biggerIsGreater = new BiggerIsGreater(w);
        return biggerIsGreater.calculate();
    }

    private String calculate() {
        calculateRecursive(originalWord, "",  originalWord.length() - 1);
        return Optional.ofNullable(result).orElse(NO_ANSWER);
    }

    private void calculateRecursive(final String word, final String currentWord, int replacedCharIndex) {
        // termination
        if(replacedCharIndex < 0) {
            if(currentWord.compareTo(originalWord) > 0) {
                if(result == null) {
                    result = currentWord;
                } else if(result.compareTo(currentWord) > 0) {
                    result = currentWord;
                }
            }
            return;
        }

        for(int i = replacedCharIndex; i >= 0; i--) {
            StringBuilder newWord = new StringBuilder(word);
            newWord.setCharAt(replacedCharIndex, word.charAt(i));
            newWord.setCharAt(i, word.charAt(replacedCharIndex));

            calculateRecursive(newWord.toString(),  word.charAt(i) + currentWord, replacedCharIndex - 1);
        }
    }
}
