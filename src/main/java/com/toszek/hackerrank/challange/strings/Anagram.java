package com.toszek.hackerrank.challange.strings;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://www.hackerrank.com/challenges/anagram/problem
 *
 * Two words are anagrams of one another if their letters can be rearranged to form the other word.
 * Given a string, split it into two contiguous substrings of equal length. Determine the minimum number of characters to change to make the two substrings into anagrams of one another.
 */
public class Anagram {
    public static int anagram(String s) {
        // check if string is dividable by 2
        if((s.length() % 2) != 0) {
            return -1;
        }

        int halfLength = s.length() / 2;
        Map<Character, Integer> differencesCounter = new HashMap<>();

        final AtomicInteger totalDifference = new AtomicInteger(halfLength); // optimization to skip calculating differences at the end;

        // add chars to differences counter
        for(int i = 0; i < halfLength; i++) {
            final Integer previousCount = differencesCounter.computeIfAbsent(s.charAt(i), k -> 0);
            differencesCounter.put(s.charAt(i), previousCount + 1);
        }
        // remove differences
        for(int i = halfLength; i < s.length(); i++) {
            differencesCounter.computeIfPresent(s.charAt(i), (key, value) -> {
                value -= 1;
                totalDifference.decrementAndGet();
                return value == 0 ? null : value;
            });
        }
        // no need to calculate sum, it's already in totalDifference
        // return differencesCounter.values().stream().reduce(0, Integer::sum);
        return totalDifference.get();
    }
}
